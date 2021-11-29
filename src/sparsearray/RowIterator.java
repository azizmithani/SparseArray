/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sparsearray;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Aziz Mithani
 */
public class RowIterator implements java.util.Iterator<ElementIterator> {
    private Iterator iterator;
    
    RowIterator(Iterator iterator) {
        this.iterator = iterator;
    }
    
    public ElementIterator next() {
        if (!this.hasNext())
            return null;
        
        LinkedList nextRow = (LinkedList)this.iterator.next();
        return new ElementIterator(nextRow.iterator(), true, ((MatrixElement)nextRow.peek()).rowIndex());
    }
    
    public boolean hasNext(){
        return iterator.hasNext();
    }
    
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
