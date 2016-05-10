package layout_personnalized;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

public class VboxLayout implements LayoutManager2 {
	
	private int vgap;
    private int minWidth = 0, minHeight = 0;
    private int preferredWidth = 0, preferredHeight = 0;
    private boolean sizeUnknown = true;
    
	private void setSizes(Container parent) {
        int nComps = parent.getComponentCount();
        Dimension d = null;

        //Reset preferred/minimum width and height.
        preferredWidth = 0;
        preferredHeight = 0;
        minWidth = 0;
        minHeight = 0;

        for (int i = 0; i < nComps; i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible()) {
                d = c.getPreferredSize();
                if (i > 0) {
                    preferredWidth += d.width/2;
                    preferredHeight += vgap;
                } else {
                    preferredWidth = d.width;
                }
                preferredHeight += d.height;
                minWidth = Math.max(c.getMinimumSize().width, minWidth);
                minHeight = preferredHeight;
            }
        }
    }
	
	@SuppressWarnings("unused")
	@Override
	public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        int maxWidth = parent.getWidth() - (insets.left + insets.right);
        int maxHeight = parent.getHeight() - (insets.top + insets.bottom);
        int nComps = parent.getComponentCount();
        int previousWidth = 0, previousHeight = 0;
        int x = 0, y = insets.top;
        int rowh = 0, start = 0;
        int xFudge = 0, yFudge = 0;
        boolean oneColumn = false;

        if (sizeUnknown) {
            setSizes(parent);
        }
        if (maxWidth <= minWidth) {
            oneColumn = true;
        }
        if (maxWidth != preferredWidth) {
            xFudge = (maxWidth - preferredWidth) / (nComps - 1);
        }
        if (maxHeight > preferredHeight) {
            yFudge = (maxHeight - preferredHeight) / (nComps - 1);
        }
        for (int i = 0 ; i < nComps ; i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible()) {
                Dimension d = c.getPreferredSize();
                x = (parent.getWidth() - d.width) / 2;
                y += previousHeight;
                c.setBounds(x, y, d.width, d.height);
                previousWidth = d.width;
                previousHeight = d.height;
            }
        }
    }

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
        Insets insets = parent.getInsets();
        dim.width = minWidth + insets.left + insets.right;
        dim.height = minHeight + insets.top + insets.bottom;
        sizeUnknown = false;
        return dim;
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
        setSizes(parent);
        Insets insets = parent.getInsets();
        dim.width = preferredWidth + insets.left + insets.right;
        dim.height = preferredHeight + insets.top + insets.bottom;
        sizeUnknown = false;
        return dim;
	}

	@Override
	public void addLayoutComponent(String arg0, Component component) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void removeLayoutComponent(Component component) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addLayoutComponent(Component parent, Object obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public float getLayoutAlignmentX(Container parent) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container parent) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void invalidateLayout(Container parent) {
		// TODO Auto-generated method stub
	}

	@Override
	public Dimension maximumLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return null;
	}
}