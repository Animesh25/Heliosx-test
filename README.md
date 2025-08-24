Java 21

Springboot 3.3.3


1. To get questions
curl --location 'localhost:8080/v1/consultation/questions'


2. To answer questions
   curl --location 'localhost:8080/v1/consultation/result' \
   --header 'Content-Type: application/json' \
   --data '[
   {
   "question": "What is your height?",
   "questionId": "f33bc9e9-5717-4d3c-84fb-8f274e99f82e",
   "answer": "123"
   },
   {
   "question": "What is your weight?",
   "questionId": "bc1a7462-1915-415d-984c-d2d529c58034",
   "answer": "123"
   },
   {
   "question": "Are you pregnant?",
   "questionId": "5402449f-32f4-4081-9732-05fedf294ecf",
   "answer": "123"
   },
   {
   "question": "What is your age?",
   "questionId": "974c0979-c275-46f0-924b-7387cd5eb095",
   "answer": "123"
   },
   {
   "question": "What is your gender?",
   "questionId": "475cd779-3bb2-4911-9a3a-e71c08bd9a0f",
   "answer": "123"
   },
   {
   "question": "Do you have any allergies?",
   "questionId": "85d0c1d3-7d9e-4ae8-92de-17f8e39d2a5a",
   "answer": "123"
   },
   {
   "question": "Do you have any chronic medical conditions?",
   "questionId": "8b2ba5ed-884e-4259-95d8-f10720f05d98",
   "answer": "123"
   }
   ]'