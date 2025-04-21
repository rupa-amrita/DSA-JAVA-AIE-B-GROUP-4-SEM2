document.addEventListener("DOMContentLoaded", function () {
    let userCredentials = JSON.parse(localStorage.getItem("userCredentials")) || {}; 

    // Login Form
    document.getElementById("loginForm").addEventListener("submit", function (event) {
        event.preventDefault();
        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value;

        if (userCredentials[username] && userCredentials[username].password === password) {
            document.getElementById("loginMessage").textContent = "Login successful!";
            document.getElementById("loginMessage").style.color = "green";

            localStorage.setItem("currentUser", username);

            setTimeout(() => {
                document.getElementById("loginContainer").classList.add("hidden");
                document.getElementById("app").classList.remove("hidden");
            }, 1000);
        } else {
            document.getElementById("loginMessage").textContent = "Invalid credentials!";
            document.getElementById("loginMessage").style.color = "red";
        }
    });

    // Create Account Form
    document.getElementById("signupForm").addEventListener("submit", function (event) {
        event.preventDefault();
        const fullName = document.getElementById("fullName").value.trim();
        const phoneNumber = document.getElementById("phoneNumber").value.trim();
        const newUsername = document.getElementById("newUsername").value.trim();
        const newPassword = document.getElementById("newPassword").value;

        if (userCredentials[newUsername]) {
            document.getElementById("signupMessage").textContent = "Username already exists!";
            document.getElementById("signupMessage").style.color = "red";
        } else {
            userCredentials[newUsername] = { password: newPassword, fullName, phoneNumber };
            localStorage.setItem("userCredentials", JSON.stringify(userCredentials));

            document.getElementById("signupMessage").textContent = "Account created successfully!";
            document.getElementById("signupMessage").style.color = "green";

            setTimeout(() => {
                document.getElementById("signupContainer").classList.add("hidden");
                document.getElementById("loginContainer").classList.remove("hidden");
            }, 1500);
        }
    });

    // Show Signup Page
    document.getElementById("createAccountBtn").addEventListener("click", function () {
        document.getElementById("loginContainer").classList.add("hidden");
        document.getElementById("signupContainer").classList.remove("hidden");
    });

    // Show Login Page
    document.getElementById("backToLoginBtn").addEventListener("click", function () {
        document.getElementById("signupContainer").classList.add("hidden");
        document.getElementById("loginContainer").classList.remove("hidden");
    });

    // Logout Functionality
    document.getElementById("logoutBtn").addEventListener("click", function () {
        localStorage.removeItem("currentUser");
        document.getElementById("app").classList.add("hidden");
        document.getElementById("loginContainer").classList.remove("hidden");

        document.getElementById("username").value = "";
        document.getElementById("password").value = "";
        document.getElementById("calorieInput").value = "";
        document.getElementById("result").innerHTML = "";
    });
});

// Meal Plan Data
const mealPlans = {
    "vegetarian": {
        2200: ["Oatmeal with fruits", "Lentil soup", "Grilled tofu with quinoa"],
        1800: ["Smoothie bowl", "Chickpea salad", "Vegetable stir-fry"],
        2150: ["Soya chunks", "Blackberry smoothie", "Roasted almonds"],
        2250: ["Spinach", "Beans", "Greek yogurt", "White bean stew"],
        1900: ["Peanut butter", "Banana cinnamon toast", "Walnuts"],
        1950: ["Chappati","Panner","Green Gram","Almonds"],
        1200: ["Veggie rice bowl", "Fruit bowl", "Raspberries"],
        2100: ["vegatable salad", "berries", "protein bars"],
        2300: ["Urad Dal","Cashwes","Mushroom curry"],
    },
    "keto": {
        2200: ["Eggs & avocado", "Grilled salmon", "Chicken & broccoli"],
        1800: ["Cheese omelet", "Keto salad", "Beef stir-fry"],
        2000: ["Albacore tuna", "Broccoli", "Cheese"],
        1200: ["Pork", "Cauliflower", "Sour cream", "Fatty fish"],
        1900: ["Egg yolks", "Heavy cream", "Coconut butter", "Bok choy"],
        2220: ["Cocoa butter", "Turnip", "Bone broth", "Mutton soup"],
        2150: ["Leafy greens", "Strawberries", "Nut butter", "Whole eggs"],
        1700: ["Nut", "berries", "protein shake", "chicken salad"],
        2400: ["Chicken fry","Brown rice","Seeds"]
    }
};

// User Data for Best Nutrition Match
const users = [
    { name: "Dharani", diet_type: "keto", calorie_needs: 2100 },
    { name: "Rupa", diet_type: "vegetarian", calorie_needs: 2200 },
    { name: "Vignesh", diet_type: "keto", calorie_needs: 1700 },
    { name: "Varsha", diet_type: "vegetarian", calorie_needs: 1800 },
    { name: "Krish", diet_type: "vegetarian", calorie_needs: 1200 },
    { name: "Laddu", diet_type: "keto", calorie_needs: 1300 },
    { name: "Bunty", diet_type: "vegetarian", calorie_needs: 1400 },
    { name: "Teja", diet_type: "vegetarian", calorie_needs: 1500 },
    { name: "Phool", diet_type: "keto", calorie_needs: 1600 },
    { name: "Prabhas", diet_type: "keto", calorie_needs: 1100 },
    { name: "Kalki", diet_type: "vegetarian", calorie_needs: 2300 },
    { name: "Pourinima", diet_type: "keto", calorie_needs: 2050}
];

// Find Best Match & Meal Plan
function findDietPlan() {
    let calorieInput = document.getElementById("calorieInput").value;
    let resultDiv = document.getElementById("result");

    let bestMatch = users.reduce((best, user) =>
        Math.abs(user.calorie_needs - calorieInput) < Math.abs(best.calorie_needs - calorieInput) ? user : best);

    let mealPlan = mealPlans[bestMatch.diet_type.toLowerCase()][bestMatch.calorie_needs] || ["No suitable meal plan found."];

    resultDiv.innerHTML = `<h3>Best Nutrition Match: ${bestMatch.name}</h3>
                           <p>Diet Type: ${bestMatch.diet_type}</p>
                           <h4>Meal Plan:</h4>
                           <ul>${mealPlan.map(meal => `<li>${meal}</li>`).join('')}</ul>`;
}
