public class TreeNode {
    String data;
    TreeNode left, right;
    int occurrence = 0;

    public TreeNode(String data) {
        this.data = data;
        occurrence++;
        left = right = null;
    }

    @Override
    public String toString() {
        return data + " " + occurrence + "\n";
    }

}
