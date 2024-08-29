# Email Sender
### Overview
The Email Sender project is a robust application designed to facilitate the sending of emails through a user-friendly interface. Built using React for the frontend, and Spring Boot with Java for the backend, this application aims to provide a seamless experience for users looking to manage and send emails efficiently.
### Features
- **User-Friendly Interface:** Responsive and intuitive frontend built with React, HTML, and CSS.
- **Email Composition:** Easily compose and send emails with rich-text formatting.Support for various email formats (HTML, plain text).
- **Attachment Support:** Attach files of various formats to your emails.
- **Secure Communication:** Backend built with Spring Boot ensuring secure and reliable email sending.Utilizes encryption and secure protocols.
## Tech Stack

- **Frontend:**
  - React
  - HTML
  - CSS

- **Backend:**
  - Spring Boot
  - Java
## Getting Started

To get started with the Email Sender project, follow these steps:

### Prerequisites

- **Node.js** and **npm** (for running the frontend)
- **Java 11+** and **Maven** (for running the backend)
  
### Frontend Setup

1. **Clone the repository:**
   
   ```bash
   git clone https://github.com/yourusername/email-sender.git
   cd email-sender/frontend
   
2. **Install the dependencies:**
   npm install
   
3. **Run the dev server:**
   npm start

### Backend Setup

1. **Clone the repository:**

   git clone https://github.com/yourusername/email-sender.git
   cd email-sender/backend

2. **Build and run:**
   mvn spring-boot:run

## Configuration
- **SMTP Settings**: Configure SMTP server settings in application.properties located in the backend/src/main/resources directory.
  
  ```bash
   spring.mail.host=smtp.example.com
   spring.mail.port=587
   spring.mail.username=yourusername@example.com
   spring.mail.password=yourpassword

- **IMAP Settings**: Configure IMAP server settings in application.properties located in the backend/src/main/resources directory.
  
  ```bash
     spring.mail.imap.host=imap.example.com
     spring.mail.imap.port=993
     spring.mail.imap.username=yourusername@example.com
     spring.mail.imap.password=yourpassword
     spring.mail.imap.ssl.enable=true

![image](https://github.com/user-attachments/assets/e4ba42dc-c12d-4b72-96ef-5c5a99e6a09d)
