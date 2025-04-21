import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Collections;

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

public class DietarySystemGUI extends JFrame {
    private JTextField calorieInput;
    private JTextArea outputArea;
    private DietaryBST bst;

    static Map<String, Map<Integer, List<String>>> mealPlans = new HashMap<>();

    static {
        mealPlans.put("vegetarian", new HashMap<>());
        mealPlans.get("vegetarian").put(2150, Arrays.asList("soya chunks", "blackberry smoothie", "roasted almonds"));
        mealPlans.get("vegetarian").put(2250, Arrays.asList("spinach", "beans", "greek yogurt", "white bean"));

        mealPlans.put("keto", new HashMap<>());
        mealPlans.get("keto").put(2150, Arrays.asList("leafy greens", "strawberries", "nut butter", "whole eggs"));
        mealPlans.get("keto").put(2220, Arrays.asList("cocoa butter", "turnip", "bone broth", "mutton soup"));
    }

    public DietarySystemGUI() {
        setTitle("Dietary Profile Matcher");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter Calorie Needs: "));
        calorieInput = new JTextField(10);
        inputPanel.add(calorieInput);
        JButton searchButton = new JButton("Find Meal Plan");
        inputPanel.add(searchButton);
        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        bst = loadProfiles();

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findAndDisplayMealPlan();
            }
        });
    }

    private void findAndDisplayMealPlan() {
        try {
            int calorie = Integer.parseInt(calorieInput.getText().trim());
            DietaryProfile match = bst.findBestMatch(calorie);
            if (match != null) {
                List<String> mealPlan = generateMealPlan(match);
                StringBuilder sb = new StringBuilder();
                sb.append("Best Match: ").append(match.name)
                        .append("\nDiet Type: ").append(match.dietType)
                        .append("\nCalories: ").append(match.calorieNeeds)
                        .append("\n\nRecommended Meal Plan:\n");
                for (String meal : mealPlan) {
                    sb.append("- ").append(meal).append("\n");
                }
                outputArea.setText(sb.toString());
            } else {
                outputArea.setText("No matching profile found.");
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Please enter a valid number.");
        }
    }

    private DietaryBST loadProfiles() {
        DietaryBST bst = new DietaryBST();
        bst.insert(new DietaryProfile("SURESH", 25, 168, 79, "Moderately Active", "keto", "30-45%", 2100));
        bst.insert(new DietaryProfile("RAMESH", 21, 167, 74, "Sedentary", "vegetarian", "50-65%", 2200));
        bst.insert(new DietaryProfile("NARESH", 20, 156, 75, "Active", "keto", "35-45%", 1700));
        bst.insert(new DietaryProfile("RADHA", 22, 155, 65, "Moderately Active", "vegetarian", "50-75%", 1800));
        bst.insert(new DietaryProfile("CHERRY", 21, 175, 62, "Moderately Active", "keto", "70-80%", 2000));
        bst.insert(new DietaryProfile("KARNA", 24, 160, 80, "Active", "keto", "65-80%", 2150));
        return bst;
    }

    public static List<String> generateMealPlan(DietaryProfile profile) {
        Map<Integer, List<String>> dietPlan = mealPlans.get(profile.dietType.toLowerCase());
        if (dietPlan != null && dietPlan.containsKey(profile.calorieNeeds)) {
            return dietPlan.get(profile.calorieNeeds);
        }
        return Collections.singletonList("No suitable meal plan found. Try adjusting your calorie intake.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DietarySystemGUI().setVisible(true));
    }
}
