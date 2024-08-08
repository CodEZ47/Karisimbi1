# !/bin/bash

# Check if email is provided
if [ -z "$1" ]; then
    echo "Email is not provided."
    exit 1
fi

EMAIL=$1

# Source file where the users' information is stored
SOURCE_FILE="user-store.txt"

# Destination directory to save the CSV file
DESTINATION_DIR="downloaded_files"

# Destination CSV file
CSV_FILE="$DESTINATION_DIR/all_users_data.csv"

# Check if the email belongs to an admin user
if grep -qP "$EMAIL, ADMIN," $SOURCE_FILE; then
    echo "Admin verified. Compiling users' data..."

    # Create the destination directory if it doesn't exist
    mkdir -p "$DESTINATION_DIR"

    # Extract the users' info from the source file and save it to the CSV file
    # Assuming each line in the source file is formatted as "USER_ID,EMAIL,ROLE,DATA1,DATA2,..."
    awk 'BEGIN {OFS=","} {print $0}' "$SOURCE_FILE" > "$CSV_FILE"

    # Check if the operation was successful
    if [ $? -eq 0 ] && [ -s "$CSV_FILE" ]; then
        echo "All users' data has been compiled into $CSV_FILE."
        exit 0
    else
        echo "Failed to compile users' data."
        exit 1
    fi
else
    echo "Access denied. Email is not associated with an admin account."
    exit 1
fi