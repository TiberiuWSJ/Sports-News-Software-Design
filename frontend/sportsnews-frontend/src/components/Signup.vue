<!-- eslint-disable vue/multi-word-component-names -->

<template>
  <div class="signup-container">
    <h1>Sign Up</h1>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="user.email" required>
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="user.password" required>
      </div>
      <div class="form-group">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" v-model="user.firstName" required>
      </div>
      <div class="form-group">
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" v-model="user.lastName" required>
      </div>
      <div class="form-group" v-if="errorMessage">
        <p class="error-message">{{ errorMessage }}</p>
      </div>
      <button type="submit">Sign Up</button>
    </form>
  </div>
</template>
  
  <script>
  import axios from 'axios';  // Correct import statement
  export default {
    name: 'Signup',
    data() {
      return {
        user: {
          email: '',
          password: '',
          firstName: '',
          lastName: '',
        },
        errorMessage: '', // To store error messages from signup failures
      };
    },
    methods: {
      submitForm() {
        // Define the endpoint URL
        const apiUrl = 'http://localhost:8080/users'; // Adjust this URL based on your server configuration
  
        // Make the POST request
        axios.post(apiUrl, this.user)
          .then(response => {
            console.log('Signup successful', response.data);
            // Redirect to the login page or dashboard upon successful signup
            this.$router.push('/login');
          })
          .catch(error => {
          console.error('There was an error during the signup', error);
          if (error.response && error.response.status === 409) {
            // Specifically handle the 409 status code
            this.errorMessage = error.response.data || 'One of the fields already in use.'; // Use server message if available
          } else {
            this.errorMessage = 'Failed to signup. Please try again later.';
          }
        });
      }
    }
  }
  </script>
  
  
  <style scoped>
  .signup-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
  }
  
  .form-group {
    margin-bottom: 20px;
  }
  .error-message {
  color: red;
  margin-top: 10px;
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
  </style>
  