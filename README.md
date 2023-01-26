# MemRepo

## Introduction
Do you need to study and hate using flash cards? Do you need to memorize your line for a play? <br>
MemRepo can help with that: <br>
- Create and store multiple memory snippets <br>
- Enter the text you wanna memorize/study <br>
- Use your voice to test yourself <br>
- See exactly where you're having problems <br>

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

## Requirement 100.0: Practice Memory Snippit

### SCENARIO
As a user interested in memorizing snippets, I want to be able to practice the snippets I wrote, using the microphone, <br>
and seeing feedback on how accurately I can recite the snippet.

### DEPENDENCIES
User written snippets are available and accessible. <br>
The user has enabled speech recognition settings.

### ASSUMPTIONS
The snippet text matches with the user’s default language. 

### EXAMPLES
#### 1.1
**Given** a memory snippet is available <br>
**When** I click the microphone button <br>
**Then** the text above the microphone should change to listening and capture my voice as I speak

#### 1.2
**Given** a memory snippet is available <br>
**When** I click microphone and don’t speak <br>
**Then** I should receive and error message: <br>
Error could not recognize voice

#### 1.3 
**Given** the order of the planets’ memory snippet is available <br>
**Given** the microphone is active <br>
**When** I speak the order: Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune <br>
**Then** all of my answer’s snippet text should appear green.

#### 1.4 
**Given** the order of the planets’ memory snippet is available <br>
**Given** the microphone is active <br>
**When** I speak the order: Mercury, Venus, Mars, Earth, Jupiter, Saturn, Uranus, Neptune <br>
**Then** the answer text of Mercury, Venus, Jupiter, Saturn, Uranus, and Neptune <br>
should appear green, and the answer text of Earth and Mars should appear red. 

#### 1.5
**Given** the order of the planets’ memory snippet is available <br>
**Given** the microphone is active <br>
**When** I speak the order: Earth, Mercury, Venus, Uranus, Neptune, Mars, Jupiter, Saturn <br>
**Then** all of my answer’s text should appear red. 

## Requirement 101.0: Modify Memory Snippet

### SCENARIO
As a user interested in memory snippets, I want to be able to create a memory snippet, edit my memory snippet, and,<br> 
delete my memory snippet. 

### DEPENDENCIES
Memory snippet data is available and accessible. 

### ASSUMPTIONS
The snippet text matches user’s default language.

### Examples
#### 1.1  
**Given** the option to add snippets is available <br>
**When** I click on the add button <br>
**Then** I should see a popup to show me a snippet creation form. 

#### 1.2 
**Given** the planet order memory snippet is available <br>
**When** I click on the more options button <br>
**Then** I should see an option to edit or delete the notecard. 

#### 1.3  
**Given** the planet order memory snippet is available <br>
**When**: <br>
- I click on the more options button 
- I click the edit option <br>
**Then** I should see a popup with an editable snippet form.

#### 1.4  
**Given** the planet order memory snippet is available <br> 
**When**: <br>
- I click on the more options button 
- I click the delete option <br>
**Then** the notecard should be removed from my list of notecards. 

#### 1.5  
**Given** the option to add snippets is available <br>
**When**: <br>
- I fill out a snippet creation form for the order of Earth’s layers 
- I hit the save button <br>
**Then** when I navigate to my notecards, I should see the notecard “Earth’s Layers”. 

#### 1.6 
**Given** the Earth’s layers memory snippet is available <br>
**When**: <br>
- I edit the Earth’s layers’ notecard 
- I hit the save button <br>
**Then** when I navigate to the Earth’s layers’ notecard, I should see the saved changes I made. 

## Class Diagram

![Class Diagram for MemRepo](MemRepoClassDiagram.png)

## Class Diagram Description

**Main**: The main screen that the user sees when entering the app. Allows user to create, delete, and update a memory snippet.<br>

**SpeechRecognizer**: External API that will be used for speech recognition. The API will be integrated to recognize the user's input to check if an answer matches with the memory snippet.<br>

**Notecard**: Object class that represents all data for a specific memory snippet for a user. Includes title for question, description for memory snippet, and an ID to accurately track in the database.<br>

**ICardDAO**: Interface for the ICardService to fetch correct data by searching by ID.<br>

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
