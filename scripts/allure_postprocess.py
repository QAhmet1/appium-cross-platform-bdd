#!/usr/bin/env python3
import json
import os
import re
import sys
from glob import glob


def norm_platform(p: str) -> str:
    return (p or "").strip().lower()


def infer_platform(data: dict, forced: str | None) -> str:
    if forced and forced not in ("auto", "parallel"):
        return forced

    # Parallel mode: infer from TestNG thread label (most reliable when platform was mis-tagged)
    if forced in ("auto", "parallel"):
        for l in data.get("labels", []) or []:
            if l.get("name") == "thread" and l.get("value"):
                m = re.search(r"TestNG-tests-(\d+)", str(l["value"]))
                if m:
                    idx = int(m.group(1))
                    if idx == 2:
                        return "ios"
                    if idx == 1:
                        return "android"

    # If already present in parameters/labels, keep it
    for p in data.get("parameters", []) or []:
        if p.get("name") == "platform" and p.get("value"):
            return norm_platform(p["value"])
    for l in data.get("labels", []) or []:
        if l.get("name") == "platform" and l.get("value"):
            return norm_platform(l["value"])

    name = data.get("name") or ""
    if "[IOS]" in name.upper():
        return "ios"
    if "[ANDROID]" in name.upper():
        return "android"

    # Heuristic: if historyId already has :ios / :android suffix
    hid = (data.get("historyId") or "").lower()
    if hid.endswith(":ios"):
        return "ios"
    if hid.endswith(":android"):
        return "android"

    # Default fallback (parallel suite: missing one is typically android)
    return "android"


def ensure_list(container, key):
    v = container.get(key)
    if v is None:
        v = []
        container[key] = v
    return v


def has_platform_param(data: dict) -> bool:
    for p in data.get("parameters", []) or []:
        if p.get("name") == "platform":
            return True
    return False


def has_platform_label(data: dict) -> bool:
    for l in data.get("labels", []) or []:
        if l.get("name") == "platform":
            return True
    return False


def main():
    if len(sys.argv) < 2:
        print("Usage: allure_postprocess.py <allure-results-dir> [platform]", file=sys.stderr)
        sys.exit(2)

    results_dir = sys.argv[1]
    forced_platform = norm_platform(sys.argv[2]) if len(sys.argv) >= 3 else None
    if forced_platform == "":
        forced_platform = None

    pattern = os.path.join(results_dir, "*-result.json")
    files = sorted(glob(pattern))
    if not files:
        print(f"No result files found in {results_dir}", file=sys.stderr)
        sys.exit(1)

    for path in files:
        with open(path, "r", encoding="utf-8") as f:
            data = json.load(f)

        platform = infer_platform(data, forced_platform)
        suffix = f" [{platform.upper()}]"

        # Update name/fullName for visibility
        name = data.get("name")
        if name and not name.endswith(suffix):
            data["name"] = name + suffix

        full_name = data.get("fullName")
        if full_name and not full_name.endswith(suffix):
            data["fullName"] = full_name + suffix

        # Make historyId unique across platforms to avoid Allure merging
        hid = data.get("historyId")
        if hid:
            if not re.search(r":(ios|android)$", hid.lower()):
                data["historyId"] = f"{hid}:{platform}"
        else:
            base = data.get("fullName") or data.get("name") or "cucumber"
            data["historyId"] = f"{base}:{platform}"

        # Add platform parameter and label
        params = ensure_list(data, "parameters")
        if not has_platform_param(data):
            params.append({"name": "platform", "value": platform})

        labels = ensure_list(data, "labels")
        if not has_platform_label(data):
            labels.append({"name": "platform", "value": platform})

        with open(path, "w", encoding="utf-8") as f:
            json.dump(data, f, ensure_ascii=False)

    # In parallel mode we expect both platforms to be present
    if forced_platform in ("auto", "parallel"):
        platforms = set()
        for path in files:
            data = json.load(open(path, "r", encoding="utf-8"))
            platforms.add(infer_platform(data, None))
        if not {"android", "ios"}.issubset(platforms):
            print(f"ERROR: parallel run expected both android & ios but got: {sorted(platforms)}", file=sys.stderr)
            sys.exit(1)


if __name__ == "__main__":
    main()

