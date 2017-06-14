/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supportClasses;

import java.util.List;

import by.bsac.timetable.hibernateFiles.entity.Record;
import tableClasses.ArrayPosition;
import tableClasses.MyMultiSpanCellTable;
import timetable.view.MainForm;

/**
 *
 * @author hello
 */
public class TableTips {

//    public static String getTextForTip(MyMultiSpanCellTable table, int colIndex, int rowIndex) {
//
//        ArrayPosition position = SupportClass.findArrayPositionOfTable(table, MainForm.getTablesArray());
//
//        byte currentWeekNumber = (byte) (position.getRowIndex() + 1);
//        byte currentSubjOrdinalNumber = (byte) (rowIndex + 1);
//        byte currentWeekDay = (byte) (position.getColIndex() + 1);
//
//        List<Record> temp = SupportClass.findMainRecordsByCriterias(MainForm.getMainRecodsCollection(), currentWeekDay, currentWeekNumber, currentSubjOrdinalNumber);
//
//        for (Record obj : temp) {
//            if ((obj.getSubjectFor().getId() == 3 && colIndex == 1) || (obj.getSubjectFor().getId() == colIndex)) {
//                return obj.getSubjAndSubjType(true);//fullName
//            }
//        }
//        return "";
//    }
}
