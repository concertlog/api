#!/bin/sh
echo "🪝 Checking git hooks..."

# Check if hooks are already configured correctly
CURRENT_HOOKS_PATH=$(git config core.hooksPath)
if [ "$CURRENT_HOOKS_PATH" = ".githooks" ] && [ -x ".githooks/pre-commit.sh" ]; then
    echo "✅ Git hooks already configured and executable."
    exit 0
fi

# If we get here, either hooks path isn't set or pre-commit isn't executable
echo "📦 Setting up git hooks..."

# Set hooks path
git config core.hooksPath .githooks

# Make sure the pre-commit hook is executable
chmod +x .githooks/pre-commit

echo "✅ Git hooks installed successfully!"
