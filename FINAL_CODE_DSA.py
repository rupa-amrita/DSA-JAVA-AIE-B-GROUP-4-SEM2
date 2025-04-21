class DietaryProfile:
    def __init__(self, name, age, height, weight, activity_level, diet_type, macronutrient_ratio, calorie_needs, dietary_restrictions):
        self.name = name
        self.age = age
        self.weight = weight
        self.height = height
        self.activity_level = activity_level
        self.diet_type = diet_type
        self.calorie_needs = calorie_needs
        self.macronutrient_ratio = macronutrient_ratio
        self.dietary_restrictions = dietary_restrictions


class BSTNode:
    def __init__(self, profile):
        self.profile = profile
        self.left = None
        self.right = None


class DietaryBST:
    def __init__(self):
        self.root = None

    def insert(self, profile):
        if self.root is None:
            self.root = BSTNode(profile)
        else:
            self._insert(self.root, profile)

    def _insert(self, current, profile):
        if profile.calorie_needs < current.profile.calorie_needs:
            if current.left is None:
                current.left = BSTNode(profile)
            else:
                self._insert(current.left, profile)
        else:
            if current.right is None:
                current.right = BSTNode(profile)
            else:
                self._insert(current.right, profile)


def find_best_match(root, calorie_needs):
    best_match = None
    min_difference = float('inf')

    def inorder_traversal(node):
        nonlocal best_match, min_difference
        if node is None:
            return
        inorder_traversal(node.left)
        diff = abs(node.profile.calorie_needs - calorie_needs)
        if diff < min_difference:
            min_difference = diff
            best_match = node.profile
        inorder_traversal(node.right)

    inorder_traversal(root)
    return best_match


# Create and populate BST
diet_bst = DietaryBST()
diet_bst.insert(DietaryProfile("SURESH", 25, 168, 79, "Moderately Active", "keto", "30-45%", 2100, ["vegatable salad"]))
diet_bst.insert(DietaryProfile("RAMESH", 21, 167, 74, "Sedentary", "vegetarian", "50-65%", 2200, ["lentil soup"]))
diet_bst.insert(DietaryProfile("NARESH", 20, 156, 75, "Active", "keto", "35-45%", 1700, ["Nut"]))
diet_bst.insert(DietaryProfile("RADHA", 22, 155, 65, "Moderately Active", "vegetarian", "50-75%", 1800, ["Smoothie bowl"]))
diet_bst.insert(DietaryProfile("BOB", 23, 169, 55, "Sedentary", "keto", "45-60%", 1900, ["Egg yolks"]))
diet_bst.insert(DietaryProfile("MR.RAO", 24, 170, 60, "Active", "vegetarian", "50-70%", 1950, ["Almonds"]))
diet_bst.insert(DietaryProfile("CHERRY", 21, 175, 62, "Moderately Active", "keto", "70-80%", 2000, ["Cheese"]))
diet_bst.insert(DietaryProfile("JALEBI", 26, 165, 63, "Moderately Active", "vegetarian", "55-65%", 2300, ["Cashwes"]))
diet_bst.insert(DietaryProfile("POOJITHA", 19, 157, 57, "Sedentary", "vegetarian", "50-75%", 2350, []))
diet_bst.insert(DietaryProfile("MEGHANA", 20, 155, 58, "Active", "keto", "65-80%", 2400, ["Seeds"]))
diet_bst.insert(DietaryProfile("SHEELA", 24, 154, 59, "Moderately Active", "vegetarian", "35-55%", 2500, []))
diet_bst.insert(DietaryProfile("UMA", 23, 160, 60, "Sedentary", "vegetarian", "45-60%", 2450, []))
diet_bst.insert(DietaryProfile("VARSHA", 22, 150, 67, "Active", "keto", "30-60%", 1900, ["Heavy cream"]))
diet_bst.insert(DietaryProfile("KRISH", 19, 153, 68, "Active", "vegetarian", "40-60%", 1200, ["Raspberries"]))
diet_bst.insert(DietaryProfile("BUNTY", 18, 159, 45, "Moderately Active", "keto", "45-65%", 1800, ["keto salad"]))
diet_bst.insert(DietaryProfile("STEVE", 29, 163, 56, "Moderately Active", "vegetarian", "50-60%", 2250, ["Spinach"]))
diet_bst.insert(DietaryProfile("ARJUN", 30, 162, 78, "Sedentary", "keto", "45-75%", 2050, []))
diet_bst.insert(DietaryProfile("KARNA", 24, 160, 80, "Active", "keto", "65-80%", 2150, ["whole eggs"]))


meal_plans = {
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
}


def generate_meal_plan(profile):
    diet = profile.diet_type.lower()
    calories = profile.calorie_needs
    restrictions = [r.lower() for r in profile.dietary_restrictions]
    
    if diet in meal_plans and calories in meal_plans[diet]:
        # Filter out restricted foods
        recommended_meals = [
            meal for meal in meal_plans[diet][calories]
            if not any(restriction in meal.lower() for restriction in restrictions)
        ]
        return recommended_meals if recommended_meals else ["No suitable meals found (restrictions applied)."]
    return ["No suitable meal plan found. Try adjusting your calorie intake."]


# Try finding best match
target_calories = 2500
match = find_best_match(diet_bst.root, target_calories)

if match:
    print(f"Best Match Found:\n-------------------")
    print(f"Name: {match.name}")
    print(f"Age: {match.age}")
    print(f"Height: {match.height} cm, Weight: {match.weight} kg")
    print(f"Activity Level: {match.activity_level}")
    print(f"Diet Type: {match.diet_type}")
    print(f"Calorie Needs: {match.calorie_needs} kcal")
    print(f"Macronutrient Ratio: {match.macronutrient_ratio}")
    print(f"Dietary Restrictions: {match.dietary_restrictions}")


    

   
    
    
    
    #  Meal Plan
    meal_plan = generate_meal_plan(match)
    print("Recommended Meal Plan:\n---------------")
    for meal in meal_plan:
        print("-", meal)
else:
    print("No suitable match found.")