# Note Service
A service for creating your own notes
## Idea
The main idea is to create a simple application where you can create your own notes and like them.
# Technological stack
- SpringBoot as a skeleton framework
- MongoDB database
- Spring Web
- Spring Security
- Spring Data MongoDB
- Spring Boot Maven Plugin
- Lombok
- MapStruct
# Description:
## User
- CRUD for user (get list, create, edit, delete)
## Content
- Content is a note, consisting of one line and the number of likes. Likes can be added and removed.
## Features
- Get a list of all notes, sorting from new to old
- Both anonymous and registered users can create a post
- Notes can be liked or unfollowed
- Only registered users can like a note
- Only users with the ADMIN role can change the role of a user
- Only users with the ADMIN role can change the number of likes and creation date of a note
## Developer
Baranetskyi Vitalii

Email: vitalik.baranetskiy@gmail.com
