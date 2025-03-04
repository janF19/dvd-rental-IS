

import os
import uuid
import psycopg2
from datetime import datetime, timedelta
import random

# Load environment variables
from dotenv import load_dotenv
load_dotenv()

# Database connection parameters
db_params = {
    "dbname": os.getenv("DB_NAME"),
    "user": os.getenv("DB_USER"),
    "password": os.getenv("DB_PASSWORD"),
    "host": os.getenv("DB_HOST"),
    "port": os.getenv("DB_PORT")
}

def generate_unique_id():
    """Generate a unique UUID."""
    return str(uuid.uuid4())

def seed_categories():
    """Seed categories table with movie genres."""
    categories = [
        "Action", "Adventure", "Fantasy", "Science Fiction", 
        "Drama", "Thriller", "Comedy", "Family", "Animation", 
        "Crime", "Western"
    ]
    return [(generate_unique_id(), category) for category in categories]

def seed_actors():
    """Seed actors table with movie actors."""
    actors = [
        "Sam Worthington", "Zoe Saldana", "Sigourney Weaver", 
        "Johnny Depp", "Orlando Bloom", "Keira Knightley", 
        "Daniel Craig", "Robert Downey Jr.", "Chris Hemsworth", 
        "Mark Ruffalo", "Chris Evans", "Scarlett Johansson",
        "Tom Hardy", "Christian Bale", "Michael Caine",
        "Henry Cavill", "Ben Affleck", "Gal Gadot"
    ]
    return [(generate_unique_id(), actor) for actor in actors]

def seed_customers():
    """Seed customers table with sample customer data."""
    customers = [
        ("john.doe@example.com", "John Doe", "123-456-7890", random.uniform(0, 50)),
        ("jane.smith@example.com", "Jane Smith", "987-654-3210", random.uniform(0, 50)),
        ("mike.johnson@example.com", "Mike Johnson", "456-789-0123", random.uniform(0, 50)),
        ("sarah.williams@example.com", "Sarah Williams", "789-012-3456", random.uniform(0, 50)),
        ("david.brown@example.com", "David Brown", "234-567-8901", random.uniform(0, 50))
    ]
    return [(generate_unique_id(), email, name, phone, late_fees) for email, name, phone, late_fees in customers]

def seed_films(categories, actors):
    """Seed films table with movie data."""
    films_data = [
        ("Avatar", "Epic sci-fi adventure", random.choice(categories)[0], random.randint(1, 7), True),
        ("Pirates of the Caribbean: At World's End", "Swashbuckling adventure", random.choice(categories)[0], random.randint(1, 7), True),
        ("Spectre", "James Bond thriller", random.choice(categories)[0], random.randint(1, 7), True),
        ("The Dark Knight Rises", "Superhero epic", random.choice(categories)[0], random.randint(1, 7), True),
        ("John Carter", "Sci-fi adventure", random.choice(categories)[0], random.randint(1, 7), True),
        ("Avengers: Age of Ultron", "Superhero team-up", random.choice(categories)[0], random.randint(1, 7), True)
    ]
    
    films = []
    film_actors = []
    for title, description, category_id, rental_duration, is_available in films_data:
        film_id = generate_unique_id()
        films.append((film_id, title, description, category_id, rental_duration, is_available))
        
        # Add 3-5 random actors to each film
        film_actors_for_film = random.sample(actors, random.randint(3, 5))
        for actor in film_actors_for_film:
            film_actors.append((actor[0], film_id))
    
    return films, film_actors

def seed_rentals(films, customers):
    """Seed rentals table with rental data."""
    rentals = []
    for _ in range(20):  # Create 20 random rentals
        film = random.choice(films)
        customer = random.choice(customers)
        rental_date = datetime.now() - timedelta(days=random.randint(1, 30))
        due_date = rental_date + timedelta(days=random.randint(3, 10))
        
        # 50% chance of returned film
        return_date = rental_date + timedelta(days=random.randint(1, 10)) if random.random() > 0.5 else None
        late_fee = random.uniform(0, 20) if return_date and return_date > due_date else 0
        
        rentals.append((
            generate_unique_id(), 
            customer[0],  # customer_id
            film[0],      # film_id
            rental_date, 
            due_date, 
            return_date,
            late_fee
        ))
    
    return rentals

def seed_database():
    """Main function to seed the entire database."""
    try:
        # Establish database connection
        conn = psycopg2.connect(**db_params)
        cursor = conn.cursor()

        # Seed Categories
        categories = seed_categories()
        cursor.executemany("INSERT INTO categories (id, name) VALUES (%s, %s)", categories)

        # Seed Actors
        actors = seed_actors()
        cursor.executemany("INSERT INTO actors (id, name) VALUES (%s, %s)", actors)

        # Seed Customers
        customers = seed_customers()
        cursor.executemany("INSERT INTO customers (id, email, name, phone, late_fees) VALUES (%s, %s, %s, %s, %s)", customers)

        # Seed Films and Film_Actors
        films, film_actors = seed_films(categories, actors)
        cursor.executemany("INSERT INTO films (id, title, description, category_id, rental_duration_days, is_available) VALUES (%s, %s, %s, %s, %s, %s)", films)
        cursor.executemany("INSERT INTO film_actors (actor_id, film_id) VALUES (%s, %s)", film_actors)

        # Seed Rentals
        rentals = seed_rentals(films, customers)
        cursor.executemany("INSERT INTO rentals (id, customer_id, film_id, rental_date, due_date, return_date, late_fee) VALUES (%s, %s, %s, %s, %s, %s, %s)", rentals)

        # Commit changes
        conn.commit()
        print("Database seeded successfully!")

    except (Exception, psycopg2.Error) as error:
        print(f"Error seeding database: {error}")
    
    finally:
        # Close database connection
        if conn:
            cursor.close()
            conn.close()
            print("Database connection closed.")

if __name__ == "__main__":
    seed_database()