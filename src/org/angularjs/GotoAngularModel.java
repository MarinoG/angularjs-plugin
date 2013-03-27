package org.angularjs;

import com.intellij.codeInspection.InspectionProfileEntry;
import com.intellij.ide.util.gotoByName.ChooseByNameBase;
import com.intellij.ide.util.gotoByName.SimpleChooseByNameModel;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.ui.SimpleColoredComponent;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.speedSearch.SpeedSearchUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johnlindquist
 * Date: 3/26/13
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class GotoAngularModel extends SimpleChooseByNameModel{

    private final Map<String, AngularItem> angularItems = new HashMap<String, AngularItem>();

    public GotoAngularModel(@NotNull Project project) {
        super(project, "AngularJS", "Help id");

        AngularItem oneCtrl = new AngularItem("OneCtrl");
        AngularItem twoCtrl = new AngularItem("TwoCtrl");
        AngularItem threeCtrl = new AngularItem("ThreeCtrl");
        AngularItem fourCtrl = new AngularItem("FourCtrl");
        AngularItem fiveCtrl = new AngularItem("FiveCtrl");
        AngularItem sixCtrl = new AngularItem("SixCtrl");
        AngularItem sevenCtrl = new AngularItem("SevenCtrl");
        AngularItem eightCtrl = new AngularItem("EightCtrl");
        AngularItem nineCtrl = new AngularItem("NineCtrl");
        angularItems.put(oneCtrl.getItemName(), oneCtrl);
        angularItems.put(twoCtrl.getItemName(), twoCtrl);
        angularItems.put(threeCtrl.getItemName(), threeCtrl);
        angularItems.put(fourCtrl.getItemName(), fourCtrl);
        angularItems.put(fiveCtrl.getItemName(), fiveCtrl);
        angularItems.put(sixCtrl.getItemName(), sixCtrl);
        angularItems.put(sevenCtrl.getItemName(), sevenCtrl);
        angularItems.put(eightCtrl.getItemName(), eightCtrl);
        angularItems.put(nineCtrl.getItemName(), nineCtrl);
    }

    //these are searched
    @Override
    public String[] getNames() {
        return ArrayUtil.toStringArray(angularItems.keySet());
    }

    //list provided to the item renderer
    @Override
    protected Object[] getElementsByName(String name, String pattern) {
        return new Object[]{angularItems.get(name)};
    }

    @Override
    public ListCellRenderer getListCellRenderer() {
        return new GotoAngularCellRenderer();
    }

    @Nullable
    @Override
    public String getElementName(Object element) {
        return "Element name!";
    }

    protected class GotoAngularCellRenderer implements ListCellRenderer {
        private final SimpleTextAttributes SELECTED;
        private final SimpleTextAttributes PLAIN;


        public GotoAngularCellRenderer() {
            SELECTED = new SimpleTextAttributes(UIUtil.getListSelectionBackground(),
                    UIUtil.getListSelectionForeground(),
                    JBColor.RED,
                    SimpleTextAttributes.STYLE_PLAIN);
            PLAIN = new SimpleTextAttributes(UIUtil.getListBackground(),
                    UIUtil.getListForeground(),
                    JBColor.RED,
                    SimpleTextAttributes.STYLE_PLAIN);

        }

        @Override
        public Component getListCellRendererComponent(JList jList, Object value, int i, boolean sel, boolean focus) {
            JPanel jPanel = new JPanel(new BorderLayout());
            jPanel.setOpaque(true);

            final Color bg = sel ? UIUtil.getListSelectionBackground() : UIUtil.getListBackground();
            final Color fg = sel ? UIUtil.getListSelectionForeground() : UIUtil.getListForeground();
            jPanel.setBackground(bg);
            jPanel.setForeground(fg);

            SimpleTextAttributes attr = sel ? SELECTED : PLAIN;
            if (value instanceof AngularItem) {
                AngularItem item = (AngularItem) value;
                final SimpleColoredComponent c = new SimpleColoredComponent();
                SpeedSearchUtil.appendColoredFragmentForMatcher("  " + item.getItemName(), c, attr, null, bg, sel);
                jPanel.add(c, BorderLayout.WEST);

                final SimpleColoredComponent group = new SimpleColoredComponent();
                SpeedSearchUtil.appendColoredFragmentForMatcher(item.getItemName() + "  ", group, attr, null, bg, sel);
                final JPanel right = new JPanel(new BorderLayout());
                right.setBackground(bg);
                right.setForeground(fg);
                right.add(group, BorderLayout.CENTER);
                jPanel.add(right, BorderLayout.EAST);
            }
            else {
                // E.g. "..." item
                return ChooseByNameBase.renderNonPrefixSeparatorComponent(UIUtil.getListBackground());
            }

            return jPanel;
        }
    }

    protected class AngularItem {
        private String itemName;

        private AngularItem(String itemName) {
            this.itemName = itemName;
        }


        public String getItemName() {
            return itemName;
        }
    }
}