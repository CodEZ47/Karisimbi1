#!/bin/bash

USER_STORE="./data/user-store.txt"

function hash_password() {
    echo -n "$1" | sha256sum | awk '{print $1}'
}

function onBoardUser() {
    # echo "Onboarding user with email"
    email=$1
    role=$2
    echo "Registering user with email: $email and role: $role"

    if [[ -z "$email" || -z "$role" ]]; then
        echo "Error: Email and role are required."
        exit 1
    fi

    uuid=$(uuidgen)
    # hashed_password=$(hash_password "defaultPassword")
    if echo "$email,$uuid,$role" >> $USER_STORE; then
        echo "User registered with UUID: $uuid"
        exit 0
    else
        echo "Error: Failed to write to user store."
        exit 1
    fi
}

function login_user() {
    email=$1
    password=$2
    hashed_password=$(hash_password "$password")

    while IFS=, read -r stored_email stored_uuid stored_hashed_password stored_role
    do
        if [[ "$stored_email" == "$email" && "$stored_hashed_password" == "$hashed_password" ]]; then
            echo "$stored_role"
            return
        fi
    done < $USER_STORE

    # Return an empty string if login fails
    echo ""
}


case $1 in
    onBoardUser)
        onBoardUser $2 $3
        ;;
    login)
        login_user $2 $3
        ;;
    *)
        echo "Usage: $0 {onBoardUser|login} args"
        ;;
esac
