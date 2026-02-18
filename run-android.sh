#!/usr/bin/env bash
set -e

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Clean ONLY Android allure outputs
rm -rf "${ROOT_DIR}/allure-results-android" "${ROOT_DIR}/allure-report-android"

# Run Android suite (uses allure-results-android via Maven profile)
mvn test -Pandroid

# Normalize Allure results (platform tag + unique historyId)
python3 "${ROOT_DIR}/scripts/allure_postprocess.py" "${ROOT_DIR}/allure-results-android" android

# Ensure we have a single Allure UI instance
pkill -f "allure.*open" 2>/dev/null || true
pkill -f "allure.*serve" 2>/dev/null || true

# Generate and open Allure report for Android (single instance)
allure generate "${ROOT_DIR}/allure-results-android" -o "${ROOT_DIR}/allure-report-android" --clean
allure open "${ROOT_DIR}/allure-report-android"

