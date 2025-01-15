//File Reading
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

//Exeption Handeling
import java.io.IOException;
import java.io.FileNotFoundException;

//Data Structers
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

//Project Classes
import dataStructers.GeneralTree;
import dataStructers.GeneralTreeIntefrace;
import models.Data;
import models.TreeNode;

public class Main {
    //General Data Structers
    static GeneralTreeIntefrace<Data> tree;
    static HashMap<Integer, Data> hashMap;

    //File Paths
    static String linksPath = "dataSets\\treeoflife_links.csv";
    static String nodesPath = "dataSets\\treeoflife_nodes.csv";
    static String outputPath = "outputs\\pre_order.txt";

    public static void main(String[] args) {
        menu();
    }

    // Menu
    static void menu() {
        while(true) {
            System.out.println();
            System.out.println("1. Load the dataset from the file");
            System.out.println("2. Search for the species");
            System.out.println("3. Traverse the tree");
            System.out.println("4. Print the subtree");
            System.out.println("5. Print the path of the species");
            System.out.println("6. Find the common ancestor of the two species");
            System.out.println("7. Calculate the tree statistics");
            System.out.println("8. Find the longest paths");
            System.out.println("9. Exit");
            System.out.println();

            System.out.println("Enter the option: ");
            String option = System.console().readLine();

            try {
                int choice = Integer.parseInt(option);

                switch(choice) {
                    case 0:
                        // 0. Secret option
                        // Load the dataset from just the nodes file
                        loadDatasetAlternative();
                        break;
                    case 1:
                        // 1. Load the dataset from the files
                        loadDataset();
                        break;
                    case 2:
                        // 2. Search for the species
                        searchForSpecies();
                        break;
                    case 3:
                        // 3. Traverse the tree
                        travenseTree();
                        break;
                    case 4:
                        // 4. Print the subtree
                        printSubTree();
                        break;
                    case 5:
                        // 5. Print the path of the species
                        printPathOfSpecies();
                        break;
                    case 6:
                        // 6. Find the common ancestor of the two species
                        commonAncestorSpecies();
                        break;
                    case 7:
                        // 7. Calculate the tree statistics
                        calculateTreeStatistics();
                        break;
                    case 8:
                        // 8. Find the longest paths
                        printLongestPaths();
                        break;
                    case 9:
                        // Exit
                        System.out.println("bye");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option. Please enter a valid option.");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }  catch (Exception e) {
                System.out.println("An unexpected error occurred.");
            }
        }
    }


    /// MENU METHODS START
    
    // Load the dataset from the files
    /**
     * Loads the dataset from the specified CSV files and constructs the evolutionary tree.
     * 
     * This method reads data from two CSV files: "treeoflife_nodes.csv" and "treeoflife_links.csv".
     * The first file contains the nodes of the tree, and the second file contains the links between these nodes.
     * 
     * If the tree is already loaded, the method will print a message and return.
     * 
     * The method performs the following steps:
     * 1. Reads the nodes from "treeoflife_nodes.csv" and creates a tree structure.
     * 2. Reads the links from "treeoflife_links.csv" and establishes parent-child relationships in the tree.
     * 
     * The method handles various exceptions that may occur during file reading and data processing:
     * - FileNotFoundException: If either of the CSV files is not found.
     * - IOException: If an I/O error occurs while reading the files.
     * - NumberFormatException: If there is an error in parsing numeric values from the files.
     * - Exception: For any other unexpected errors.
     * 
     * Upon successful loading, the method prints a success message.
     */
    static void loadDataset() {

        if(tree != null) {
            System.out.println("Tree already loaded.");
            return;
        }

        System.out.println();
        System.out.println("Loading tree from the file...");

        hashMap = new HashMap<Integer, Data>();

        
        try (BufferedReader br = new BufferedReader(new FileReader(nodesPath))) {
            br.readLine();

            String line = br.readLine();
                
            Data data = lineConverterForLoadDataset(line);
            tree = new GeneralTree<Data>(data);
            hashMap.put(data.getId(), data);

            while(br.ready()) {
                line = br.readLine();
                
                data = lineConverterForLoadDataset(line);

                hashMap.put(data.getId(), data);
            }

        } catch (FileNotFoundException e) {
            System.out.println("The nodes file was not found.");
            System.out.println("Please check read the README");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An I/O error occurred while reading the nodes file.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("A number format error occurred while processing the nodes file.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while loading the dataset from the nodes file.");
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(linksPath))) {
            br.readLine();

            while(br.ready()) {
                String[] lines = br.readLine().split(",");

                Data parent = hashMap.get(Integer.parseInt(lines[0]));
                Data child = hashMap.get(Integer.parseInt(lines[1]));
                
                TreeNode<Data> parentNode = tree.findNode(parent);
                TreeNode<Data> childNode = new TreeNode<Data>(child, parentNode);
                tree.addChild(parentNode, childNode);
            }

        }  catch (FileNotFoundException e) {
            System.out.println("The links file was not found.");
            System.out.println("Please check read the README");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An I/O error occurred while reading the links file.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("A number format error occurred while processing the links file.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while loading the dataset from the links file.");
            e.printStackTrace();
        }

        System.out.println("Tree loaded successfully.");
    }

    // Load the dataset from just the nodes file
    /**
     * Loads the dataset from the "treeoflife_nodes.csv" file and constructs the evolutionary tree.
     * If the tree is already loaded, it will print a message and return.
     * 
     * The method reads the first line of the file to initialize the root of the tree and then
     * iteratively reads the remaining lines to construct the tree structure.
     * 
     * It handles various exceptions that might occur during file reading and processing:
     * - FileNotFoundException: If the nodes file is not found.
     * - IOException: If an I/O error occurs while reading the nodes file.
     * - NumberFormatException: If a number format error occurs while processing the nodes file.
     * - Exception: If any other unexpected error occurs while loading the dataset.
     * 
     * Upon successful loading, it prints a success message.
     */
    static void loadDatasetAlternative() {
        
        if(tree != null) {
            System.out.println("Tree already loaded.");
            return;
        }

        System.out.println();
        System.out.println("Loading tree from the file...");
        System.out.println("Mirliva was here.");

        try (BufferedReader br = new BufferedReader(new FileReader(nodesPath))) {

            br.readLine();

            String line = br.readLine();
            Data data = lineConverterForLoadDataset(line);

            tree = new GeneralTree<Data>(data);

            hashMap = new HashMap<Integer, Data>();
            hashMap.put(data.getId(), data);

            for (int i = 0; i < data.getNumberOfChildren(); i++) {
                readLineForLoadDataset(br, data.getNumberOfChildren(), data);
            }

        } catch (FileNotFoundException e) {
            System.out.println("The nodes file was not found.");
            System.out.println("Please check read the README");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An I/O error occurred while reading the nodes file.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("A number format error occurred while processing the nodes file.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while loading the dataset from the nodes file.");
            e.printStackTrace();
        }

        System.out.println("Tree loaded successfully.");
    }

    // Search for the species
    /**
     * Searches for a species in the evolutionary tree by its ID.
     * 
     * This method prompts the user to enter a species ID and searches for the corresponding
     * species in the tree. If the tree is not loaded, it informs the user and exits. If the
     * species is found, it prints the species data; otherwise, it informs the user that the
     * species was not found.
     * 
     * The method handles the following exceptions:
     * - NumberFormatException: If the user input is not a valid number.
     * - NullPointerException: If the species ID does not exist in the tree.
     * - Exception: For any other unexpected errors.
     */
    static void searchForSpecies() {

        if(tree == null) {
            System.out.println("Tree not loaded.");
            return;
        }

        System.out.println();

        Data data = null;
        while(true) {
            System.out.println("Enter the species id to search: ");
            String id = System.console().readLine();

            try {
                int speciesId = Integer.parseInt(id);
                data = hashMap.get(speciesId);
                break;
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (NullPointerException e) {
                System.out.println("Species not found.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred.");
            }
        }

        if (data == null) {
            System.out.println("Species not found.");
        } else {
            data.print();
        }
    }

    // Traverse the tree
    /**
     * Traverses the evolutionary tree and writes the pre-order traversal to a file.
     * 
     * This method checks if the tree is loaded. If not, it prints a message and returns.
     * If the tree is loaded, it traverses the tree in pre-order and writes the traversal
     * to a file named "pre_order.txt".
     * 
     * Exceptions:
     * - NumberFormatException: Thrown if there is an invalid number format in the input.
     * - NullPointerException: Thrown if a species is not found in the tree.
     * - Exception: Catches any other unexpected exceptions.
     */
    static void travenseTree() {

        if(tree == null) {
            System.out.println("Tree not loaded.");
            return;
        }

        System.out.println();
        System.out.println("Traversing the tree...");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write(convertToStringForSubTree(tree.getRoot()));
        } catch(FileNotFoundException e) {
            System.out.println("The output file was not found.");
            System.out.println("Please check read the README");
        } catch (IOException e) {
            System.out.println("An I/O error occurred while writing the output file.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } catch (NullPointerException e) {
            System.out.println("Species not found.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred.");
        }
        
        System.out.println("Tree traversed successfully.");
    }

    // Print the subtree
    /**
     * Prints the subtree of a specified species in the evolutionary tree.
     * 
     * This method prompts the user to enter a species ID, finds the corresponding node
     * in the tree, and prints the subtree rooted at that node. If the tree is not loaded,
     * it informs the user. It handles invalid inputs and species not found scenarios.
     * 
     * Exceptions:
     * - NumberFormatException: If the input is not a valid number.
     * - NullPointerException: If the species ID does not exist in the tree.
     * - Exception: For any other unexpected errors.
     */
    static void printSubTree() {

        if(tree == null) {
            System.out.println("Tree not loaded.");
            return;
        }

        System.out.println();

        TreeNode<Data> node = null;
        while(true) {
            System.out.println("Enter the species id to print the subtree: ");
            String id = System.console().readLine();

            try {
                int speciesId = Integer.parseInt(id);
                node = tree.findNode(hashMap.get(speciesId));
                break;
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (NullPointerException e) {
                System.out.println("Species not found.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred.");
            }
        }

        System.out.println(convertToStringForSubTree(node));
    }

    // Print the path of the species
    /**
     * Prints the path of a species from the root of the tree to the specified species node.
     * 
     * This method prompts the user to enter a species ID, finds the corresponding node in the tree,
     * and then prints the path from the root to that node. If the tree is not loaded, it will notify the user.
     * 
     * The method handles the following exceptions:
     * - NumberFormatException: If the user input is not a valid number.
     * - NullPointerException: If the species is not found in the tree.
     * - Exception: For any other unexpected errors.
     */
    static void printPathOfSpecies() {

        if(tree == null) {
            System.out.println("Tree not loaded.");
            return;
        }

        System.out.println();
        TreeNode<Data> node = null;

        while(true) {
            System.out.println("Enter the species id to print the path: ");
            String id = System.console().readLine();

            try {
                int speciesId = Integer.parseInt(id);
                node = tree.findNode(hashMap.get(speciesId));
                break;
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (NullPointerException e) {
                System.out.println("Species not found.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred.");
            }
        }
        
        Stack<TreeNode<Data>> path = new Stack<TreeNode<Data>>();

        path.push(node);
        while(node.getParent() != null) {
            node = node.getParent();
            path.push(node);
        }

        while(!path.isEmpty()) 
        System.out.println(convertToString(path.peek(), path.pop().getDepth()));


        
    }
    
    // Find the common ancestor of the two species
    /**
     * This method finds and prints the common ancestor of two species in an evolutionary tree.
     * It prompts the user to enter the IDs of two species, finds the corresponding nodes in the tree,
     * and then determines their common ancestor.
     * 
     * If the tree is not loaded, it prints an error message and returns.
     * If the user enters an invalid ID or the species is not found, it prints an appropriate error message.
     * 
     * The method prints the details of the first species, the second species, and their common ancestor.
     * 
     * Exceptions:
     * - NumberFormatException: If the user input is not a valid number.
     * - NullPointerException: If the species is not found in the tree.
     * - Exception: For any other unexpected errors.
     */
    static void commonAncestorSpecies() {

        if(tree == null) {
            System.out.println("Tree not loaded.");
            return;
        }

        System.out.println();

        TreeNode<Data> firstNode = null;
        while(true) {
            System.out.println("Enter the first species id to find the common ancestor: ");
            String id = System.console().readLine();

            try {
                int speciesId = Integer.parseInt(id);
                firstNode = tree.findNode(hashMap.get(speciesId));
                break;
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (NullPointerException e) {
                System.out.println("Species not found.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred.");
            }
        }

        TreeNode<Data> secondNode = null;
        while(true) {
            System.out.println("Enter the second species id to find the common ancestor: ");
            String id = System.console().readLine();

            try {
                int speciesId = Integer.parseInt(id);
                secondNode = tree.findNode(hashMap.get(speciesId));
                break;
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (NullPointerException e) {
                System.out.println("Species not found.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred.");
            }
        }

        TreeNode<Data> commonParent = findCommonParent(firstNode, secondNode);

        System.out.println("First species: " + convertToString(firstNode, 0));
        System.out.println("Second species: " + convertToString(secondNode, 0));
        System.out.println("Common parent: " + convertToString(commonParent, 0));
    }
    
    // Calculate the tree statistics
    /**
     * Calculates and prints the statistics of the evolutionary tree.
     * The statistics include the height, degree, and breadth of the tree.
     * If the tree is not loaded, it prints a message indicating that the tree is not loaded.
     * 
     * The method performs the following steps:
     * 1. Checks if the tree is loaded. If not, prints "Tree not loaded." and returns.
     * 2. Prints a message indicating that the tree statistics are being calculated.
     * 3. Calls the calculateHeightAndDegree method to get the height and degree of the tree.
     * 4. Calls the calculateBreadth method to get the breadth of the tree.
     * 5. Prints the height, degree, and breadth of the tree.
     */
    static void calculateTreeStatistics() {

        if(tree == null) {
            System.out.println("Tree not loaded.");
            return;
        }

        System.out.println();
        System.out.println("Calculating tree statistics...");
        int[] result = calculateHeightAndDegree();

        int height = result[0];
        int degree = result[1];
        int breadth = calculateBreadth();
        
        System.out.println("Height: " + height);
        System.out.println("Degree: " + degree);
        System.out.println("Breadth: " + breadth);

    }
    
    // Find the longest paths
    /**
     * Prints the longest paths in the evolutionary tree.
     * 
     * This method performs the following steps:
     * 1. Checks if the tree is loaded. If not, prints a message and returns.
     * 2. Finds all leaf nodes in the tree.
     * 3. Determines the longest paths from the root to the leaf nodes.
     * 4. Prints the count and length of the longest paths.
     * 5. Prints each of the longest paths.
     * 
     * The method uses a queue to store leaf nodes and another queue to store the longest paths.
     * It iterates through the leaf nodes to find the maximum depth and stores the nodes with the maximum depth.
     * Finally, it prints each path from the leaf node to the root.
     */
    static void printLongestPaths() {
        
        if(tree == null) {
            System.out.println("Tree not loaded.");
            return;
        }

        System.out.println();
        System.out.println("Finding the longest paths...");

        Iterator<Map.Entry<Integer, Data>> iterator  = hashMap.entrySet().iterator();
        Queue <TreeNode<Data>> leafsQueue = new LinkedList<TreeNode<Data>>();
        Queue <TreeNode<Data>> paths = new LinkedList<TreeNode<Data>>();

        // Find all leafs
        while(iterator.hasNext()) {
            Map.Entry<Integer, Data> dataNode = iterator.next();
            Data data = dataNode.getValue();
            if(data.getNumberOfChildren() == 0) {
                leafsQueue.add(tree.findNode(data));
            }
        }

        int lengthOfQueue = leafsQueue.size();
        int maxDepth = 0;

        // Find the longest paths
        for(int i = 0; i < lengthOfQueue; ++i) {
            TreeNode<Data> node = leafsQueue.poll();
            leafsQueue.add(node);

            int depth = node.getDepth();

            if(depth > maxDepth) {
                paths.clear();
                maxDepth = depth;
                paths.add(node);
            }
            else if(depth == maxDepth) {
                paths.add(node);
            }
            
        }

        System.out.println("Longest paths found successfully.");
        System.out.println("Count of longest paths: " + paths.size());
        System.out.println("Length of the longest paths: " + maxDepth);

        // Print the longest paths
        while(!paths.isEmpty()) {
            TreeNode<Data> node = paths.poll();
            
            Stack<TreeNode<Data>> path = new Stack<TreeNode<Data>>();

            path.push(node);
            while(node.getParent() != null) {
                node = node.getParent();
                path.push(node);
            }

            while(!path.isEmpty())
            System.out.println(convertToString(path.peek(), path.pop().getDepth()));
        }
    }
    /// MENU METHODS END


    /// HELPER METHODS START
    /**
     * Read the line from the file and create the tree
     * 
     * @param br        BufferedReader
     * @param child_count number of children
     * @param parent    parent node
     */
    private static void readLineForLoadDataset(BufferedReader br, int child_count, Data parent) {
        if (child_count < 0) {
            return;
        }

        try {

            String line = br.readLine();

            Data data = lineConverterForLoadDataset(line);
            
            TreeNode<Data> parentNode = tree.findNode(parent);
            TreeNode<Data> childNode = new TreeNode<Data>(data, parentNode);
            tree.addChild(parentNode, childNode);

            hashMap.put(data.getId(), data);

            for(int i = 0; i < data.getNumberOfChildren(); i++) {
                readLineForLoadDataset(br, data.getNumberOfChildren(), data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Convert the line to Data object
     * 
     * @param line line from the file
     * @return Data object
     */
    private static Data lineConverterForLoadDataset(String line) {

        int index = 0;
        String[] termString = line.split(",");
        int id = Integer.parseInt(termString[index]);
        index++;

        String name = "";
        if (termString[index].contains("\"")) {
            name = termString[index] + ",";
            index++;

            while (!termString[index].contains("\"")) {
                name += termString[index] + ",";
                index++;
            }
            name += termString[index];
            index++;

            name.substring(1, name.length() - 2);
        } else {
            name = termString[index];
            index++;
        }

        return new Data(
                id,
                name,
                Integer.parseInt(termString[index]),
                Integer.parseInt(termString[index + 1]),
                Integer.parseInt(termString[index + 2]),
                Integer.parseInt(termString[index + 3]),
                Integer.parseInt(termString[index + 4]),
                Integer.parseInt(termString[index + 5]));
    }

    /**
     * Convert the tree to string
     * 
     * @param node root node
     * @return converted text
     */
    private static String convertToStringForSubTree(TreeNode<Data> node) {
        String text = "";

        text = convertToStringForSubTree(node, 0, text);

        return text;
    }
    
    /**
     * Convert the tree to string
     * 
     * @param node root node
     * @param depth depth of the tree
     * @param text text to be converted
     * @return converted text
     */
    private static String convertToStringForSubTree(TreeNode<Data> node, int depth, String text) {
        String line = convertToString(node, depth);

        text += line + "\n";
        for (TreeNode<Data> child : node.getChildren()) {
            text = convertToStringForSubTree(child, depth + 1, text);
        }

        return text;
    }

    /**
     * Convert the node to string
     * 
     * @param node root node
     * @param depth depth of the tree
     * @return converted text
     */
    private static String convertToString(TreeNode<Data> node, int depth) {
        String line = "";

        for (int i = 0; i < depth; i++) 
        line += "-";
        
        line += node.getData().getId();
        line += "-" + node.getData().getName();
        line += " (" + ((node.getData().isLeaf()) ? "-" : "+") + ")";

        return line;
    }

    /**
     * Find the common parent of the two species
     * 
     * @param firstNode 
     * @param secondNode
     * @return common parent
     */
    private static TreeNode<Data> findCommonParent(TreeNode<Data> firstNode, TreeNode<Data> secondNode) {

        while(firstNode.getData().getId() != secondNode.getData().getId()) {
            if (firstNode.getDepth() > secondNode.getDepth()) {
                firstNode = firstNode.getParent();
            } else {
                secondNode = secondNode.getParent();
            }
        }

        return firstNode;

    }
    
    /**
     * Calculate the height and degree of the tree
     * 
     * @return height and degree
     */
    private static int[] calculateHeightAndDegree() {
        return calculateHeightAndDegree(tree.getRoot(), 0, 0);
    }

    /**
     * Calculate the height and degree of the tree
     * 
     * @param node
     * @param height height of the tree
     * @param degree degree of the tree
     * @return height and degree
     */
    private static int[] calculateHeightAndDegree(TreeNode<Data> node, int height, int degree) {

        if(node.getChildren().size() == 0) {
            if(height < node.getDepth()) {
                height = node.getDepth();
            }
        }
        else {
            if(degree < node.getChildren().size()) {
                degree = node.getChildren().size();
            }

            for (TreeNode<Data> child : node.getChildren()) {
                int[] result = calculateHeightAndDegree(child, height, degree);

                if (result[0] > height) {
                    height = result[0];
                }

                if (result[1] > degree) {
                    degree = result[1];
                }
            }
        }
        
        return new int[] {height, degree};
    }
    
    /**
     * Calculate the breadth of the tree
     * 
     * @return breadth of the tree
     */
    private static int calculateBreadth() {
        int breadth = 0;

        Iterator<Map.Entry<Integer, Data>> iterator  = hashMap.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry<Integer, Data> entry = iterator.next();
            Data data = entry.getValue();
            if(data.getNumberOfChildren() == 0) {
                breadth++;
            }
        }

        return breadth;
    }

    /// HELPER METHODS END
}
