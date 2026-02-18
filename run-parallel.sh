#!/usr/bin/env bash
set -e

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Clean ONLY parallel (cross-platform) allure outputs
rm -rf "${ROOT_DIR}/allure-results-parallel" "${ROOT_DIR}/allure-report-parallel"

# Run parallel cross-platform suite (Android + iOS in same JVM)
mvn test -Pparallel

# Ensure Allure results are not merged and include platform for all tests
python3 "${ROOT_DIR}/scripts/allure_postprocess.py" "${ROOT_DIR}/allure-results-parallel" parallel

# Ensure we have a single Allure UI instance
pkill -f "allure.*open" 2>/dev/null || true
pkill -f "allure.*serve" 2>/dev/null || true

# Generate and open Allure report for parallel run (single instance)
allure generate "${ROOT_DIR}/allure-results-parallel" -o "${ROOT_DIR}/allure-report-parallel" --clean
allure open "${ROOT_DIR}/allure-report-parallel"

