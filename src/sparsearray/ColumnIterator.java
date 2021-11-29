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
public class ColumnIterator implements java.util.Iterator<ElementIterator> {
    
    private Iterator iterator;
    
    ColumnIterator(Iterator iterator) {
        this.iterator = iterator;
    }
    
    public ElementIterator next() {
        if (!this.hasNext())
            return null;
        
        LinkedList nextColumn = (LinkedList)this.iterator.next();
        return new ElementIterator(nextColumn.iterator(), true, ((MatrixElement)nextColumn.peek()).columnIndex());
    }
    
    public boolean hasNext(){
        return iterator.hasNext();
    }
    
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
