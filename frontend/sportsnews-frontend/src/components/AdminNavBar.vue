<template>
  <nav class="navbar">
    <div class="menu">
      <router-link to="/admin/users" class="nav-link">Manage Users</router-link>
      <router-link to="/admin/stories" class="nav-link">Manage Stories</router-link>
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
      <div class="dropdown">
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
    </div>
  </nav>
</template>

<script>
import axios from 'axios';

export default {
  name: 'AdminNavbar',
  data() {
    return {
      isOpen: false,
      isNotificationOpen: false,
      notifications: []
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
  },
  methods: {
    toggleDropdown() {
      this.isOpen = !this.isOpen;
    },
    toggleNotificationDropdown() {
      this.isNotificationOpen = !this.isNotificationOpen;
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
    async handleNotificationClick(notification) {
      try {
        await this.deleteNotification(notification.id);
        this.$router.push({ name: 'StoryDetail', params: { id: notification.relatedStory.storyID } });
      } catch (error) {
        console.error('Error handling notification click:', error);
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
    logout() {
      localStorage.clear();
      this.$router.push('/login');
    },
    profileView() {
      this.$router.push('/profile');
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
  display: flex;
  align-items: center;
  position: relative;
}

.nav-link {
  margin-right: 20px;
  color: white;
  text-decoration: none;
}

.nav-link:hover {
  text-decoration: underline;
}

.notification-container {
  position: relative;
  margin-right: 20px;
  cursor: pointer;
  color: white;
}

.notification-dropdown {
  position: absolute;
  right: 0;
  top: 100%;
  background-color: #f9f9f9;
  min-width: 300px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.notification-item {
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

.notification-item p {
  margin: 0;
  color: black;
}

.no-notifications {
  color: grey;
  font-style: italic;
}

.dropdown {
  position: relative;
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
  margin-left: 10px;
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
</style>
