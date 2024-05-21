<template>
  <div class="profile-container">
    <h2>User Profile</h2>
    <form @submit.prevent="updateProfile">
      <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="user.email" disabled />
      </div>
      <div class="form-group">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" v-model="user.firstName" required />
      </div>
      <div class="form-group">
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" v-model="user.lastName" required />
      </div>
      <div class="form-group">
        <label for="favoriteTeam">Favorite Team:</label>
        <input type="text" id="favoriteTeam" v-model="user.favoriteTeam" />
      </div>
      <div class="form-group">
        <label for="favoriteSportsman">Favorite Sportsman:</label>
        <input type="text" id="favoriteSportsman" v-model="user.favoriteSportsman" />
      </div>
      <button type="submit">Save Changes</button>
    </form>
    <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Profile',
  data() {
    return {
      user: {
        id: null,
        email: '',
        firstName: '',
        lastName: '',
        favoriteTeam: '',
        favoriteSportsman: ''
      },
      errorMessage: ''
    };
  },
  mounted() {
    this.fetchUserProfile();
  },
  methods: {
    async fetchUserProfile() {
      try {
        const userEmail = localStorage.getItem('userEmail'); // Assuming email is stored in localStorage
        const response = await axios.get(`http://localhost:8080/users/email/${userEmail}`);
        this.user = response.data;
      } catch (error) {
        console.error('Error fetching user profile:', error);
        this.errorMessage = 'Failed to fetch user profile';
      }
    },
    async updateProfile() {
      try {
        const response = await axios.put(`http://localhost:8080/users/${this.user.id}`, {
          firstName: this.user.firstName,
          lastName: this.user.lastName,
          favoriteTeam: this.user.favoriteTeam,
          favoriteSportsman: this.user.favoriteSportsman
        });
        this.user = response.data;
        alert('Profile updated successfully!');
      } catch (error) {
        console.error('Error updating profile:', error);
        this.errorMessage = 'Failed to update profile';
      }
    }
  }
};
</script>

<style scoped>
.profile-container {
  padding: 20px;
  margin-top: 60px;
}

.form-group {
  margin-bottom: 20px;
}

input {
  width: 100%;
  padding: 10px;
  margin-top: 5px;
}

button {
  padding: 10px 20px;
  cursor: pointer;
}

.error-message {
  color: red;
  margin-top: 10px;
}
</style>
