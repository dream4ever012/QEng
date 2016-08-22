import csv

def csvRNS(csvName, col_num):
    import csv
    file = open(csvName)
    csv = csv.reader(file)

    col_val =[]
    i = True
    for row in csv:
        if (i == False):
            col_val.append(row[col_num])
        if (i == True):
            col_names = row
            i = False
    col_val.sort()
    return col_val

def vectorTup(col_val_table, col_val_TM):
    tmp = 0
    NDV_tup_cnt = []
    len_TM = len(col_val_TM)
    row_val = ''
    hold = 0

    for j in col_val_table:
        temp_tup = ()
        for k in range(hold, len_TM):
            hold = hold + 1
            row_val = col_val_TM[k]
            if (j != row_val): # j does not exist in k
                hold = hold - 1
                break
            elif (j==row_val):
                tmp = tmp + 1
                if (hold == len_TM):
                    break
        temp_tup = j, tmp
        NDV_tup_cnt.append(temp_tup)
        tmp = 0
    return NDV_tup_cnt

def createVecTup(TableName, col_num_T, TMName, col_num_TM):
    col_val_T = csvRNS(TableName, col_num_T)
    col_val_TM = csvRNS(TMName, col_num_TM)
    return vectorTup(col_val_T, col_val_TM)

def SumOfVecMulp(TableName, col_num_T, TMName1, col_num_TM1,
                 TMName2, col_num_TM2):
    VTres1 = createVecTup(TableName, col_num_T, TMName1, col_num_TM1)
    VTres2 = createVecTup(TableName, col_num_T, TMName2, col_num_TM2)
    length = len(VTres1)
    sum_total = 0
    temp = 0
    for i in range(0,length):
        temp = VTres1[i][1] * VTres2[i][1]
        sum_total = sum_total + temp
    return sum_total

result = createVecTup('UC.csv', 0, 'GaaUC.csv',1)

print SumOfVecMulp('UC.csv', 0, 'GaaUC.csv',1, 'UCaaUCS.csv', 0)
print SumOfVecMulp('UCS.csv', 0, 'UCaaUCS.csv',1, 'UCSaaEC.csv', 0)
#print SumOfVecMulp('EC.csv', 0, 'UCSaaEC.csv',1, 'ECaaECS.csv', 1)


#VTres_EC_UCS_EC = createVecTup('EC.csv', 0, 'UCSaaEC.csv', 1)
#VTres_EC_EC_ECS = createVecTup('EC.csv', 0, 'ECaaECS.csv', 1)

#col_val_ECT = csvRNS('EC.csv', 0)
#col_val_UCSaaECTM = csvRNS('UCSaaEC.csv',1)



#col_val_GT = csvRNS('G.csv', 0)
#col_val_GTM = csvRNS('GaaUC.csv',0)
#NDV_tup_cnt = vectorTup(col_val_GT, col_val_GTM)



#for row in col_val:
    #temp = col_val.count(x)
#print col_names






