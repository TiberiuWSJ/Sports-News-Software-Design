<template>
  <div class="login-container">
    <h1>Login Page</h1>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="credentials.email" required>
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="credentials.password" required>
      </div>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      <div class="button-container">
        <button type="submit">Login</button>
        <button type="button" @click="$router.push('/register')">Sign Up</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from 'axios'; // Make sure Axios is imported

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Login',
  data() {
    return {
      credentials: {
        email: '',
        password: ''
      },
      errorMessage: '' // To handle and display error messages
    };
  },
  methods: {
    async submitForm() {
      const loginUrl = 'http://localhost:8080/users/login'; // API endpoint URL

      try {
        const response = await axios.post(loginUrl, this.credentials);

        // Assuming the response includes the user's first and last name, isModerator, and a message
        console.log(response.data.message); // Log the message from the server

        // Store the user details in localStorage
        localStorage.setItem('userFirstName', response.data.firstName);
        localStorage.setItem('userLastName', response.data.lastName);
        localStorage.setItem('userEmail', response.data.userEmail); // Store the email for future use
        localStorage.setItem('isModerator', response.data.isModerator);

        // Redirect to the appropriate page based on the isModerator status
        if (response.data.isModerator) {
          this.$router.push('/adminView');
        } else {
          this.$router.push('/stories');
        }
      } catch (error) {
        console.error('Login error:', error);
        // Handle login failures and display error messages
        this.errorMessage = error.response && error.response.data.message
          ? error.response.data.message
          : 'Server error or network issue!';
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh; /* This makes the container full height */
}

.form-group {
  margin-bottom: 20px;
}

.error-message {
  color: red;
  margin-top: 10px;
}

input[type="email"], input[type="password"] {
  width: 100%; /* Full width */
  padding: 10px;
  margin-top: 5px;
}

button {
  padding: 10px 20px;
  cursor: pointer;
}
</style>
