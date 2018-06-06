/**
 * every internal node in the tree has four children
 * @author Margaret and Julie
 * @version 10/10/17
 * 
 *
 */
public class InternalNode extends QuadNode
{
    private QuadNode nW;
    private QuadNode nE;
    private QuadNode sE;
    private QuadNode sW;
    
    /**
     * constructor creates the internal node
     * @param flyweight is the empty node 
     */
    public InternalNode(LeafNode<?> flyweight)
    {
        //super(x, y, w, h);
        
        nW = flyweight;
        nE = flyweight;
        sE = flyweight;
        sW = flyweight;
    }
    
    /**
     * Sets the quad node to be nW
     * @param node represents the nW quad node
     */
    public void setNW(QuadNode node)
    {
        nW = node;
    }
    
    /**
     * Sets the quad node to be nE
     * @param node represents the nE quad node
     */
    public void setNE(QuadNode node)
    {
        nE = node;
    }
    
    /**
     * Sets the quad node to be sE
     * @param node represents the sE quad node
     */
    public void setSE(QuadNode node)
    {
        sE = node;
    }
    
    /**
     * Sets the quad node to be sW
     * @param node represents the sW quad node
     */
    public void setSW(QuadNode node)
    {
        sW = node;
    }
    
    /**
     * returns the NW node
     * @return nW quad node
     */
    public QuadNode getNW()
    {
        return nW;
    }
    
    /**
     * returns the NE node
     * @return nE quad node
     */
    public QuadNode getNE()
    {
        return nE;
    }
    
    /**
     * returns the SE Node
     * @return sE quad node
     */
    public QuadNode getSE()
    {
        return sE;
    }
    
    /**
     * returns the SW node
     * @return sW quad node
     */
    public QuadNode getSW()
    {
        return sW;
    }
}