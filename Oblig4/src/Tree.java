public class Tree {
    private TreeNode root;
    private int totalWords = 0;
    public Tree() {
        this.root = null;
    }
    //logic for inserting nodes into the tree
    public void insert(String value){
        TreeNode treeNode = new TreeNode(value);

        if(this.root == null){
            root = treeNode;
            return;
        }

        TreeNode current = root;

        //Insert words into the tree alphabetically, and update the number of occurrences
        while(true){
            //If the strings are identical update the occurence counter and break
            if(value.compareTo(current.data) == 0){
                current.occurrence++;
                break;
            }
            //If the new value is alphabetically before current place the value to the left
            else if(value.compareTo(current.data) < 0) {
                if(current.left == null){
                    current.left = treeNode;
                    break;
                }else
                    current = current.left;
            }
            //Otherwise put it to the right in the tree
            else {
                if(current.right == null){
                    current.right = treeNode;
                    break;
                }else{
                    current = current.right;
                }
            }
        }
    }

    public void printAlphabetically(){
        printAlphabetically(root);
    }
    //Recursively print out the tree
    public void printAlphabetically(TreeNode node){
        if(node != null){
            printAlphabetically(node.left);
            System.out.print(node);
            printAlphabetically(node.right);
        }
    }
}
