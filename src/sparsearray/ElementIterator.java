package sparsearray;

import java.util.Iterator;
/**
 *
 * @author Aziz Mithani
 */
public class ElementIterator implements java.util.Iterator<MatrixElement> {
    
    private Iterator iterator;
    private boolean isRowIterator;
    private int index;
    
    
    ElementIterator(Iterator iterator, boolean isRowIterator, int index) {
        this.iterator = iterator;
        this.isRowIterator = isRowIterator;
        this.index = index;
    }
    
    public boolean iteratingRow(){
        return isRowIterator;
    }
    
    public boolean iteratingColumn(){
        return !isRowIterator;
    }
    
    public int nonIteratingIndex() {
        return this.index;
    }
    
    public MatrixElement next(){
        if (!this.hasNext())
            return null;
        return (MatrixElement) iterator.next();
    }
    
    public boolean hasNext() {
        return iterator.hasNext();
    }
    
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
