#!/bin/bash

# Check if user ID is provided
if [ -z "$1" ]; then
    echo "User ID is not provided."
    exit 1
fi

USER_ID=$1

# Source file where the information is stored
SOURCE_FILE="user-store.txt"

# Destination directory to save the user's info file
DESTINATION_DIR="downloaded_files"

# Create the destination directory if it doesn't exist
mkdir -p "$DESTINATION_DIR"

# Temporary file to store the user's info
USER_FILE="$DESTINATION_DIR/${USER_ID}_info.txt"

# Extract the user's info from the source file and save it to the temporary file
grep -P "^${USER_ID}\b" "$SOURCE_FILE" > "$USER_FILE"

# Check if the extraction was successful
if [ $? -eq 0 ] && [ -s "$USER_FILE" ]; then
    echo "User info downloaded successfully."
    exit 0
else
    echo "Failed to download user info or user info not found."
    rm -f "$USER_FILE" # Remove the empty file if extraction failed
    exit 1
fi
