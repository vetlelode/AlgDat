public class Tree {
    private TreeNode root;

    public Tree() {
        this.root = null;
    }

    public void insert(String value){
        TreeNode treeNode = new TreeNode(value);

        if(this.root == null){
            root = treeNode;
            return;
        }

        TreeNode current = root;

        //Insert words into the tree alphabetically, and update the number of occurrences
        while(true){
            if(value.compareTo(current.data) == 0){
                current.occurrence++;
                break;
            }else if(value.compareTo(current.data) < 0) {
                if(current.left == null){
                    current.left = treeNode;
                    break;
                }else
                    current = current.left;
            }else {
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
