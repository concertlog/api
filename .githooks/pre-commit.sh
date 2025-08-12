#!/bin/sh
echo '[git hook] executing spotless:apply to format code before commit'
# Get list of staged files
STAGED_FILES=$(git diff --cached --name-only --diff-filter=ACMR)
if [ -z "$STAGED_FILES" ]; then
    echo "No files to format."
    exit 0
fi
# Check if there are unstaged changes to stash
if ! git diff --quiet; then
    STASH_NEEDED=1
    echo "Stashing unstaged changes..."
    if ! git stash push --keep-index --include-untracked; then
        echo "❌ Failed to stash changes"
        exit 1
    fi
else
    STASH_NEEDED=0
fi
# Run spotless:apply
if command -v mvn > /dev/null 2>&1
then
    mvn spotless:apply
else
    ./mvnw spotless:apply
fi
# Store the last exit code
FORMAT_RESULT=$?
# Restore unstaged changes if we stashed them
if [ "$STASH_NEEDED" -eq 1 ]; then
    echo "Restoring unstaged changes..."
    if ! git stash pop --quiet; then
        echo "❌ Failed to restore stashed changes. Your changes are safe in the stash. Please run 'git stash pop' manually."
        exit 1
    fi
fi
# If spotless failed, show error and exit
if [ $FORMAT_RESULT -ne 0 ]; then
    echo "❌ Spotless failed to format files"
    exit 1
fi
# Stage all formatted files that were previously staged
for file in $STAGED_FILES; do
    git add "$file"
done
echo "✅ Formatting complete - proceeding with commit!"
exit 0
