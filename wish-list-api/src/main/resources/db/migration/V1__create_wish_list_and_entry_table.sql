CREATE TABLE IF NOT EXISTS wish_list (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(75) NOT NULL,
    note VARCHAR(255),
    owner_email VARCHAR(150) NOT NULL,
    created_date TIMESTAMP DEFAULT NOW(),
    updated_date TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS wish_list_entry (
    id SERIAL NOT NULL PRIMARY KEY,
    amount INT,
    note VARCHAR(255) NOT NULL,
    reference VARCHAR(150) NOT NULL,
    wish_list_id INT NOT NULL,
    created_date TIMESTAMP DEFAULT NOW(),
    updated_date TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (wish_list_id) REFERENCES wish_list (id) ON DELETE CASCADE
);