db.createUser(
    {
        user: "skyjo",
        pwd: "skyjo",
        roles: [
            {
                role: "readWrite",
                db: "skyjo"
            },
            {
                role: "readWrite",
                db: "test"
            }
        ]
    }
);