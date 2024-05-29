<template>
  <nav class="navbar">
    <div class="menu">
      <div class="notification-container" @click="toggleNotificationDropdown">
        <i class="fas fa-bell"></i>
        <div v-if="isNotificationOpen" class="notification-dropdown">
          <div v-if="notifications.length === 0" class="notification-item no-notifications">
            <p>No new notifications about stories of your favourite sportsman/team. If you haven’t set one yet, you can do it in your profile.</p>
          </div>
          <div v-else v-for="notification in notifications" :key="notification.id" class="notification-item" @click="handleNotificationClick(notification)">
            <p>{{ notification.content }}</p>
          </div>
        </div>
      </div>
      <div class="favorites-container" @click="toggleFavoritesDropdown">
        <i class="fas fa-heart"></i>
        <div v-if="isFavoritesOpen" class="favorites-dropdown">
          <div v-if="favorites.length === 0" class="favorites-item no-favorites">
            <p>No saved stories. Start adding your favorite stories!</p>
          </div>
          <div v-else v-for="favorite in favorites" :key="favorite.storyID" class="favorites-item">
            <p @click="handleFavoriteClick(favorite)" class="favorite-title">{{ favorite.title }}</p>
            <span class="delete-favorite" @click.stop="handleDeleteFavorite(favorite)">&times;</span>
          </div>
        </div>
      </div>
      <input type="text" v-model="searchQuery" @input="performSearch" placeholder="Search stories..." class="search-input" />
      <div v-if="searchResults.length > 0" class="search-dropdown">
        <div v-for="story in searchResults" :key="story.storyID" class="search-item" @click="goToStory(story)">
          <p>{{ story.title }}</p>
        </div>
      </div>
      <button @click="toggleDropdown" class="dropdown-button">
        {{ userName }}
        <i class="fas fa-chevron-down"></i>
      </button>
      <div v-if="isOpen" class="dropdown-content">
        <a href="#" @click="profileView">Profile</a>
        <a href="#">Settings</a>
        <a href="#" @click="logout">Logout</a>
      </div>
    </div>
  </nav>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Navbar',
  data() {
    return {
      isOpen: false,
      isNotificationOpen: false,
      isFavoritesOpen: false,
      notifications: [],
      favorites: [],
      searchQuery: '',
      searchResults: []
    };
  },
  computed: {
    userName() {
      const firstName = localStorage.getItem('userFirstName');
      return `${firstName}`;
    }
  },
  mounted() {
    this.fetchNotifications();
    this.fetchFavorites();
  },
  methods: {
    toggleDropdown() {
      this.isOpen = !this.isOpen;
    },
    logout() {
      localStorage.clear();
      this.$router.push('/login');
    },
    profileView() {
      this.$router.push('/profile');
    },
    toggleNotificationDropdown() {
      this.isNotificationOpen = !this.isNotificationOpen;
    },
    toggleFavoritesDropdown() {
      this.isFavoritesOpen = !this.isFavoritesOpen;
    },
    async fetchNotifications() {
      try {
        const userEmail = localStorage.getItem('userEmail');
        const response = await axios.get(`http://localhost:8080/notifications/user/${userEmail}`);
        this.notifications = response.data.sort((a, b) => b.id - a.id).slice(0, 5);
      } catch (error) {
        console.error('Error fetching notifications:', error);
      }
    },
    async fetchFavorites() {
      try {
        const userId = localStorage.getItem('userId');
        const response = await axios.get(`http://localhost:8080/favorites/user/${userId}`);
        this.favorites = response.data;
      } catch (error) {
        console.error('Error fetching favorites:', error);
      }
    },
    async handleNotificationClick(notification) {
      try {
        await this.deleteNotification(notification.id);
        this.$router.push({ name: 'StoryDetail', params: { id: notification.relatedStory.storyID } });
      } catch (error) {
        console.error('Error handling notification click:', error);
      }
    },
    async handleFavoriteClick(favorite) {
      try {
        this.$router.push({ name: 'StoryDetail', params: { id: favorite.storyID } });
      } catch (error) {
        console.error('Error handling favorite click:', error);
      }
    },
    async handleDeleteFavorite(favorite) {
      try {
        const userId = localStorage.getItem('userId');
        await axios.delete(`http://localhost:8080/favorites/user/${userId}/story/${favorite.storyID}`);
        this.favorites = this.favorites.filter(fav => fav.storyID !== favorite.storyID);
      } catch (error) {
        console.error('Error deleting favorite:', error);
      }
    },
    async deleteNotification(notificationId) {
      try {
        await axios.delete(`http://localhost:8080/notifications/${notificationId}`);
        this.notifications = this.notifications.filter(notification => notification.id !== notificationId);
      } catch (error) {
        console.error('Error deleting notification:', error);
      }
    },
    async performSearch() {
      if (this.searchQuery.trim() === '') {
        this.searchResults = [];
        return;
      }
      try {
        const response = await axios.get(`http://localhost:8080/stories/search?keyword=${this.searchQuery}`);
        this.searchResults = response.data;
      } catch (error) {
        console.error('Error searching stories:', error);
      }
    },
    goToStory(story) {
      this.$router.push({ name: 'StoryDetail', params: { id: story.storyID } });
    }
  }
};
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: flex-end;
  background-color: #333;
  padding: 10px 20px;
  color: white;
  position: fixed;
  width: 100%;
  top: 0;
  left: 0;
  z-index: 1000;
}

.menu {
  position: relative;
  display: flex;
  align-items: center;
}

.notification-container,
.favorites-container {
  position: relative;
  margin-right: 20px;
  cursor: pointer;
  color: white;
}

.notification-dropdown,
.favorites-dropdown {
  position: absolute;
  right: 0;
  top: 100%;
  background-color: #f9f9f9;
  min-width: 300px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.notification-item,
.favorites-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

.notification-item p,
.favorites-item p {
  margin: 0;
  color: black;
}

.favorite-title {
  flex-grow: 1;
  cursor: pointer;
}

.delete-favorite {
  cursor: pointer;
  color: grey;
  margin-left: 10px;
}

.no-notifications,
.no-favorites {
  color: grey;
  font-style: italic;
  padding: 10px;
}

.dropdown-button {
  background: none;
  border: none;
  color: white;
  cursor: pointer;
  font-size: 16px;
  display: flex;
  align-items: center;
}

.dropdown-button i {
  margin-left: 10px; /* Adjust the margin value to your preference */
}

.dropdown-content {
  display: block;
  position: absolute;
  right: 0;
  top: 100%;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown-content a:hover {
  background-color: #f1f1f1;
}

.search-input {
  margin-right: 20px;
  padding: 5px;
  border-radius: 4px;
  border: none;
}

.search-dropdown {
  position: absolute;
  right: 0;
  top: 100%;
  background-color: #f9f9f9;
  min-width: 300px;
  max-height: 300px;
  overflow-y: auto;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.search-item {
  padding: 10px;
  border-bottom: 1px solid #ddd;
  cursor: pointer;
}

.search-item p {
  margin: 0;
  color: black;
}

.search-item:hover {
  background-color: #f1f1f1;
}
</style>
