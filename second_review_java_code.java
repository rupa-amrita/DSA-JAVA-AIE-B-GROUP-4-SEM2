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