document.addEventListener("DOMContentLoaded", function() {
    const passwordInput = document.getElementById("passwordInput");
    const confirmInput = document.getElementById("confirmationInput");
    const submitButton = document.getElementById("submitButton");
  
    // Add an 'input' event listener to both password and confirmation inputs
    passwordInput.addEventListener("input", checkPassword);
    confirmInput.addEventListener("input", checkPassword);
  
    function checkPassword() {
      const passwordValue = passwordInput.value;
      const confirmValue = confirmInput.value;
      console.log(passwordValue + "  " + confirmValue)
      if (passwordValue === confirmValue && passwordValue!="") {
        // Passwords match, enable the submit button
        submitButton.removeAttribute("disabled");
      } else {
        // Passwords don't match, disable the submit button
        submitButton.setAttribute("disabled", "true");
      }
    }
  });