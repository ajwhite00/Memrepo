# MemRepo

## Introduction
Do you need to study and hate using flash cards? Do you need to memorize your line for a play?<br>
MemRepo can help with that:<br>
- Enter the subject you wanna study<br>
- Use your voice to test yourself<br>
- Understand exactly where your having a problem<br>

MemRepo can help you practice and memorize: <br>
- Scriptures
- Science Facts
- Poetry
- Documents
- Speeches
- Math
- Quotes
- History and Geography



## Storyboard

![Wireframe for Memrepo](MemRepoWireFrame.png)

## Functional Requirements

### SCENARIO
As a user interested in memorizing snippets, I want to be able to practice the snippets I wrote, using the microphone,<br>
and seeing feedback on how accurately I can recite the snippet.

### DEPENDENCIES
User written snippets are available and accessible.<br>
The user has enabled speech recognition settings.

### ASSUMPTIONS
The snippet text matches with the user’s default language. 

### EXAMPLES
#### 1.1
Given a memory snippet is available<br>
When I click the microphone button<br>
Then the text above the microphone changes to listening<br>
And captures my voice as I speak
#### 1.2
Given a memory snippet is available and user is speaking<br>
When I finish speaking<br>
Then the results are shown displaying the accuracy of speech captured vs snippet text
#### 1.3
Given a memory snippet is available<br>
When I click microphone and don’t speak<br>
Then I should receive and error message:<br>
	Error could not recognize voice
#### 1.4 
Given a memory snippet is available<br>
When I click on the add button<br>
Then I should have a screen to show user input for title and description.
#### 1.5 
Given a memory snippet is available<br>
When I click on the more options button<br>
Then I should have a modal to edit user input for title and description.
#### 1.6
Given a memory snippet is available<br>
When I click on the more options button<br>
Then I should have a popup to ask if the user would like to delete user information.


## Class Diagrams

--Insert class diagram--

## Description

--Insert class diagram description here--

## Responsibilities

### Roles

Product Owner / Scrum Master - Aj White<br>
Frontend Developer - Javohir Jalolitdinov<br>
Integration Developer / PM Support - Nicholas Lawson<br>
Developer Support - Pruthvi Patel<br>
Developer Support - Ravi Patel<br>

### Weekly Meetings

Standups: Mondays 11:00am - 11:15am | Thursdays 9:30am - 9:40am<br>
Development Work-Time: Tuesdays 11:00am-12:00pm | Thursdays 12:30pm - 1:30pm
