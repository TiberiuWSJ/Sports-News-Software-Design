<template>
  <div class="manage-stories">
    <h2>Manage Stories</h2>
    <form @submit.prevent="createStory">
      <div class="form-group">
        <label for="title">Title:</label>
        <input type="text" id="title" v-model="newStory.title" required />
      </div>
      <div class="form-group">
        <label for="body">Body:</label>
        <textarea id="body" v-model="newStory.body" required></textarea>
      </div>
      <div class="form-group">
        <label for="imageURL">Image URL:</label>
        <input type="text" id="imageURL" v-model="newStory.imageURL" />
      </div>
      <button type="submit">Create Story</button>
    </form>
    <h2>Existing Stories</h2>
    <div v-for="story in stories" :key="story.storyID" class="story-item">
      <h3>{{ story.title }}</h3>
      <p>{{ story.body }}</p>
      <button @click="deleteStory(story.storyID)">Delete</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      stories: [],
      newStory: {
        title: '',
        body: '',
        imageURL: ''
      }
    };
  },
  mounted() {
    this.fetchStories();
  },
  methods: {
    async fetchStories() {
      try {
        const response = await axios.get('http://localhost:8080/stories');
        this.stories = response.data;
      } catch (error) {
        console.error('Error fetching stories:', error);
      }
    },
    async createStory() {
      try {
        const response = await axios.post('http://localhost:8080/stories', this.newStory);
        this.stories.push(response.data);
        this.newStory = { title: '', body: '', imageURL: '' };
      } catch (error) {
        console.error('Error creating story:', error);
      }
    },
    async deleteStory(storyID) {
      try {
        await axios.delete(`http://localhost:8080/stories/${storyID}`);
        this.stories = this.stories.filter(story => story.storyID !== storyID);
      } catch (error) {
        console.error('Error deleting story:', error);
      }
    }
  }
};
</script>

<style scoped>
.manage-stories {
  padding: 20px;
  margin-top: 60px; /* Add this line */
}

.form-group {
  margin-bottom: 20px;
}

.story-item {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 10px;
  margin: 10px 0;
}

.story-item h3 {
  margin: 0 0 10px 0;
}

.story-item p {
  margin: 0 0 10px 0;
}

button {
  padding: 10px 20px;
  cursor: pointer;
}
</style>
