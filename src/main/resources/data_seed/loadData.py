import mysql.connector
import json
import os

# Connect to MySQL database (running in Docker container)
cnx = mysql.connector.connect(
    user='root',
    password='root',
    host='host.docker.internal',  # Use host.docker.internal for Docker containers
    port=3307,  # Update to the correct port if using port mapping (3306:3307)
    database='estore_db'
)

cursor = cnx.cursor()

# Load JSON data from file
with open('products.json', 'r', encoding='utf-8') as file:
    data = json.load(file)

for product in data['products']:
    product_name = product['name']
    product_description = product['description']
    price = product['price']
    stock_quantity = product['stock_quantity']
    seller_id = product['seller_id']

    # Construct the image file path
    image_path = f"{product_name}.webp"  # Change the extension if needed

    # Read the image file
    try:
        with open(image_path, 'rb') as img_file:
            image_data = img_file.read()
    except FileNotFoundError:
        print(f"Image file for '{product_name}' not found. Skipping this product.")
        continue

    # Insert product into products table
    product_query = """
    INSERT INTO products (name, description, price, stock_quantity, seller_id)
    VALUES (%s, %s, %s, %s, %s)
    """
    product_values = (product_name, product_description, price, stock_quantity, seller_id)

    try:
        cursor.execute(product_query, product_values)
        product_id = cursor.lastrowid  # Retrieve the product ID for the inserted product
    except mysql.connector.Error as err:
        print(f"Error inserting {product_name}: {err}")
        continue

    # Insert image into images table
    image_query = """
    INSERT INTO images (product_id, image, is_primary)
    VALUES (%s, %s, %s)
    """
    image_values = (product_id, image_data, True)  # Set 'is_primary' to True

    try:
        cursor.execute(image_query, image_values)
    except mysql.connector.Error as err:
        print(f"Error inserting image for {product_name}: {err}")
        continue

# Commit changes and close the connection
cnx.commit()
cursor.close()
cnx.close()
