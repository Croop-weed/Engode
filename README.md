# Engode 💡

**Engode** is an AI-powered Java application that converts step-by-step natural language algorithms into executable code. It uses **Mistral AI** via **Ollama** to run large language models locally and leverages **Spring Boot** to expose REST APIs for user interaction.

---

## 🚀 Features

- Convert algorithm descriptions to working code
- Supports multiple programming languages (e.g., Java, Python, C++)
- Runs entirely offline using local LLM (Mistral via Ollama)
- Simple and extensible REST API (Spring Boot)
- Easy to integrate with frontends or educational tools

---

## 🛠️ Tech Stack

- **Java**
- **Spring Boot**
- **Mistral AI** (via **Ollama** runtime)
- **MySQL** *(optional, for storing algorithms and outputs)*

---

## 🧠 How It Works

1. User submits an algorithm in plain English.
2. Spring Boot backend formats it as a prompt.
3. Prompt is sent to Mistral AI using Ollama.
4. LLM responds with code in the requested language.
5. Code is returned via API or UI and optionally stored.

---

## 📸 Screenshots

*(Add screenshots of your UI or API responses here)*

---

## 📦 Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/engode.git
