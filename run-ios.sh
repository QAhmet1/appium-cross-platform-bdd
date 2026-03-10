#!/usr/bin/env bash
set -e

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Clean ONLY iOS allure outputs
rm -rf "${ROOT_DIR}/allure-results-ios" "${ROOT_DIR}/allure-report-ios"

# Run iOS suite (uses allure-results-ios via Maven profile)
mvn test -Pios

# Normalize Allure results (platform tag + unique historyId)
python3 "${ROOT_DIR}/scripts/allure_postprocess.py" "${ROOT_DIR}/allure-results-ios" ios

# Ensure we have a single Allure UI instance
pkill -f "allure.*open" 2>/dev/null || true
pkill -f "allure.*serve" 2>/dev/null || true

# Generate and open Allure report for iOS (single instance)
allure generate "${ROOT_DIR}/allure-results-ios" -o "${ROOT_DIR}/allure-report-ios" --clean
allure open "${ROOT_DIR}/allure-report-ios"

