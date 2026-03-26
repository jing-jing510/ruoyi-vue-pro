# Errors

## 2026-03-26

- Context: Git snapshot commit in PowerShell workspace.
- Error: `&&` is not a valid statement separator in this PowerShell environment.
- Fix: Run git commands as separate shell invocations, or use PowerShell-native separators and condition checks.
