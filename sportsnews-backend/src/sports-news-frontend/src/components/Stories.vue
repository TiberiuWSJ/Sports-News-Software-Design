<template>
    <div>
      <table>
        <thead>
          <tr>
            <th>Title</th>
            <th>Published Date</th>
            <th>Story Content</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="story in stories" :key="story.storyID">
            <td>{{ story.title }}</td>
            <td>{{ formatDate(story.publishedDate) }}</td>
            <td>{{ story.body }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
<script>
  export default {
    data() {
      return {
        stories: []
      };
    },
    mounted() {
      this.fetchStories();
    },
    methods: {
      async fetchStories() {
        try {
          const response = await fetch('http://localhost:8080/stories');
          if (response.ok) {
            this.stories = await response.json();
          } else {
            throw new Error('Failed to fetch stories');
          }
        } catch (error) {
          console.error("There was an error!", error);
        }
      },
      formatDate(date) {
        return new Date(date).toLocaleDateString("en-GB");
      }
    }
  };
</script>
