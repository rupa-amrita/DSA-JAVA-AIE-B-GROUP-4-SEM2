import java.util.*;

class DietaryProfile {
    String name;
    int age, height, weight, calorieNeeds;
    String activityLevel, dietType;
    String macronutrientRatio;

    public DietaryProfile(String name, int age, int height, int weight, String activityLevel, String dietType, String macronutrientRatio, int calorieNeeds) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.dietType = dietType;
        this.macronutrientRatio = macronutrientRatio;
        this.calorieNeeds = calorieNeeds;
    }
}

class MealPlan {
    String dietType;
    int calorieNeeds;
    List<String> meals;

    public MealPlan(String dietType, int calorieNeeds, List<String> meals) {
        this.dietType = dietType.toLowerCase();
        this.calorieNeeds = calorieNeeds;
        this.meals = meals;
    }
}

class BSTNode {
    DietaryProfile profile;
    BSTNode left, right;

    public BSTNode(DietaryProfile profile) {
        this.profile = profile;
        this.left = null;
        this.right = null;
    }
}

class DietaryBST {
    BSTNode root;

    public void insert(DietaryProfile profile) {
        root = insertRec(root, profile);
    }

    private BSTNode insertRec(BSTNode node, DietaryProfile profile) {
        if (node == null) return new BSTNode(profile);
        if (profile.calorieNeeds < node.profile.calorieNeeds)
            node.left = insertRec(node.left, profile);
        else
            node.right = insertRec(node.right, profile);
        return node;
    }

    public DietaryProfile findBestMatch(int calorieNeeds) {
        BSTNode bestMatch = null;
        int minDifference = Integer.MAX_VALUE;

        BSTNode current = root;
        while (current != null) {
            int diff = Math.abs(current.profile.calorieNeeds - calorieNeeds);
            if (diff < minDifference) {
                minDifference = diff;
                bestMatch = current;
            }
            current = (calorieNeeds < current.profile.calorieNeeds) ? current.left : current.right;
        }
        return (bestMatch != null) ? bestMatch.profile : null;
    }
}

public class DietarySystem {
    static List<MealPlan> mealPlans = new ArrayList<>();

    static {
        mealPlans.add(new MealPlan("vegetarian", 2150, Arrays.asList("soya chunks", "blackberry smoothie", "roasted almonds")));
        mealPlans.add(new MealPlan("vegetarian", 2250, Arrays.asList("spinach", "beans", "greek yogurt", "white bean")));
        mealPlans.add(new MealPlan("keto", 2150, Arrays.asList("leafy greens", "strawberries", "nut butter", "whole eggs")));
        mealPlans.add(new MealPlan("keto", 2220, Arrays.asList("cocoa butter", "turnip", "bone broth", "mutton soup")));
    }

    public static List<String> generateMealPlan(DietaryProfile profile) {
        for (MealPlan plan : mealPlans) {
            if (plan.dietType.equals(profile.dietType.toLowerCase()) && plan.calorieNeeds == profile.calorieNeeds) {
                return plan.meals;
            }
        }
        return Collections.singletonList("No suitable meal plan found. Try adjusting your calorie intake.");
    }

    public static void main(String[] args) {
        DietaryBST bst = new DietaryBST();
        bst.insert(new DietaryProfile("SURESH", 25, 168, 79, "Moderately Active", "keto", "30-45%", 2100));
        bst.insert(new DietaryProfile("RAMESH", 21, 167, 74, "Sedentary", "vegetarian", "50-65%", 2200));
        bst.insert(new DietaryProfile("NARESH", 20, 156, 75, "Active", "keto", "35-45%", 1700));
        bst.insert(new DietaryProfile("RADHA", 22, 155, 65, "Moderately Active", "vegetarian", "50-75%", 1800));
        bst.insert(new DietaryProfile("CHERRY", 21, 175, 62, "Moderately Active", "keto", "70-80%", 2000));
        bst.insert(new DietaryProfile("KARNA", 24, 160, 80, "Active", "keto", "65-80%", 2150));

        // Find best match for 2150 calorie needs
        DietaryProfile match = bst.findBestMatch(2150);
        if (match != null) {
            System.out.println("Best match: " + match.name + " - " + match.dietType + " - " + match.calorieNeeds + " kcal");
            
            List<String> mealPlan = generateMealPlan(match);
            System.out.println("Recommended meal plan for " + match.name + ":");
            for (String meal : mealPlan) {
                System.out.println("- " + meal);
            }
        } else {
            System.out.println("No matching profile found.");
        }
    }
}
