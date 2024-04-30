#include <stdio.h>
#include <stdbool.h>

#define MAX_ROW 9       //do dai toi da cua cot
#define MAX_COLUMN  9   //do dai toi da cua hang
#define MAX 100         //so phan tu toi da cua hang doi



typedef struct POINT {

    int row;         // tọa độ x của nút
    int column;      // tọa độ y của nút
    int value;       // giá trị 0 hoặc 1 của nút
    bool visited;    // đánh dấu nút đã được đi qua
    struct POINT* prev;     // con trỏ trỏ đến nút liền trước đó trước khi đi đến nút này

}point_t;

point_t matrix[MAX_ROW][MAX_COLUMN];

typedef struct {
    point_t data[MAX];  // lưu trữ các nút
    int front;          // chỉ số đầu của hàng đợi
    int rear;         // chỉ số cuối của hàng đợi

}Queue;

// khoi tao ham doi
void initQueue(Queue *q) {

    q->front = 0;
    q->rear = -1;

}
//Kiểm tra xem hàng đợi có rỗng hay không?
int isEmpty(Queue q) {

    return (q.rear < q.front);

}
//Kiểm tra xem hàng đợi đã đầy chưa
int isFull(Queue q) {

    if((q.rear - q.front ) == (MAX - 1)) {
        return 1;
    }
    else {
       return 0;
    }
}

//Thêm phần tử vào cuối (rear) của hàng đợi.
void enQueue(Queue *q, point_t x) {

      if (isFull(*q)) {

           for(int i = q->front; i < q->rear; i++){
            q->data[i - q->front] = q->data[i];
           }
           q->rear = MAX - q->front;
           q->front = 0;
      }

        q->rear = q->rear + 1;
        q->data[q->rear] = x;

}

//Lấy phần tử đầu (front) ra khỏi hàng đợi
point_t deQueue(Queue *q) {

    point_t d;  // Tạo một biến để lưu trữ giá trị của phần tử đầu tiên

    if (!isEmpty(*q)) { // Kiểm tra xem hàng đợi có rỗng không
       d = q->data[q->front];// Lấy giá trị của phần tử đầu tiên
       q->front = q->front +1; // Di chuyển front lên một vị trí
    }

    if (q->front > q->rear) { // Kiểm tra xem nếu front vượt quá rear
        initQueue(q);  // Khởi tạo lại hàng đợi để tránh xung đột với rear
    }
    return d; // Trả về giá trị của phần tử đầu tiên

}

//kiểm tra liệu một điểm có nằm trong giới hạn của ma trận hay không.
bool checkCoordinate(int dong, int cot) {
    bool ret = false;
    if((0 <= dong && dong < MAX_ROW) && (0 <= cot && cot < MAX_COLUMN)){
        ret = true;
    }
    return ret;
}
//Hàm tìm điểm xung quanh có giá trị là 1
void findSurroundingPoint(int dong, int cot, point_t surroundingPnt[4], int* count) {
    // Khởi tạo số biến đếm số lượng các nút có thể đi được quanh 1 vị trí
    int tempCnt = 0;
    // Kiểm tra xem vị trí điểm bên phải có đến được không?
    if ((checkCoordinate(dong, cot + 1) == true) && (matrix[dong][cot + 1].value == 1)) {
        surroundingPnt[tempCnt] = matrix[dong][cot + 1];
        tempCnt++;
    }

    // Kiểm tra xem vị trí điểm bên dưới có đến được không?
    if ((checkCoordinate(dong + 1, cot) == true) && (matrix[dong + 1][cot].value == 1)) {
        surroundingPnt[tempCnt] = matrix[dong + 1][cot];
        tempCnt++;
    }

    // Kiểm tra xem vị trí điểm bên trái có đến được không?
    if ((checkCoordinate(dong, cot - 1) == true) && (matrix[dong][cot - 1].value == 1)) {
        surroundingPnt[tempCnt] = matrix[dong][cot - 1];
        tempCnt++;
    }

    // Kiểm tra xem vị trí điểm bên trên có đến được không?
    if ((checkCoordinate(dong - 1, cot) == true) && (matrix[dong - 1][cot].value == 1)) {
        surroundingPnt[tempCnt] = matrix[dong - 1][cot];
        tempCnt++;
    }

    *count = tempCnt; // Trả về số lượng nút xung quanh và cập nhật biến count


}

// Ham tim duong di ngan nhat
void findShortestPath(int dong, int cot) {
    Queue queue;
    initQueue(&queue);

    matrix[0][0].visited = true;
    enQueue(&queue, matrix[0][0]);

    bool found = false;

    while (!isEmpty(queue) && found == false ) {
        point_t p = deQueue(&queue);
        int count;
        point_t pp[4];
        findSurroundingPoint(p.row, p.column, pp, &count);

        for (int i = 0; i < count; i++) {
            if (pp[i].visited == false) {
                matrix[pp[i].row][pp[i].column].visited = true;
                matrix[pp[i].row][pp[i].column].prev = &matrix[p.row][p.column];

                if (pp[i].row == dong && pp[i].column == cot) {
                    found = true;
                    break;
                } else {
                    enQueue(&queue, pp[i]);
                }
            }
        }
    }
    //kikiem tra xem co duong di tu (0,0) den (dong,cot) khong
    if (found == true) {
        point_t *pmatrix = &matrix[dong][cot];
        int count = 0;
        point_t mang[MAX]; // luu tru gia tri cua moi diem duong di

        while(pmatrix != NULL){
            mang[count] = *pmatrix;
            pmatrix = pmatrix->prev;
            count++;
        }

        printf("Duong di ngan nhat tu O(0,0) den A(%d,%d) co do dai la %d o:\n", dong,cot, count);

        for(int i = count -1; i>= 0; i--){
            printf("(%d,%d)", mang[i].row, mang[i].column);
            if(i != 0){
                printf("->");
            }
        }


    } else{
            printf("Khong co duong di tu O(0,0) den A(%d,%d)\n", dong, cot);
    }
}



int main()
{
    int input[MAX_ROW][MAX_COLUMN] = {
        {1,0,0,0,1,0,1,1,0},
        {1,1,0,1,1,1,0,0,1},
        {0,1,0,1,1,0,1,0,1},
        {0,1,1,0,0,1,0,1,1},
        {0,0,1,0,1,0,1,0,0},
        {1,1,1,0,0,0,0,1,1},
        {1,0,1,1,1,1,1,0,0},
        {1,1,1,0,0,0,1,0,1},
        {0,0,0,1,1,1,1,1,0},
    };
    printf(" CHUONG TRINH TIM DUONG DI NGAN NHAT TU O(0,0) DEN A(dong, cot): \n\n");

    printf("Ma tran 9x9 bieu dien cho mat phang 2 chieu\n");
    for(int i = 0; i< 9; i++){
        for(int j = 0; j < 9; j++){
            printf("%d ", input[i][j]);
        }
        printf("\n");
    }
    //
    printf("\nToa do cua o xuat phat la O(0,0).\n\n");
    //khoi tao ma tran va diem dich
     for (int i = 0; i < MAX_ROW; i++) {
        for (int j = 0; j < MAX_COLUMN; j++) {
            matrix[i][j].row = i;
            matrix[i][j].column = j;
            matrix[i][j].value = input[i][j];
            matrix[i][j].visited = false;
            matrix[i][j].prev = NULL;
        }
    }
    int dong, cot;
    printf("Hay nhap toa do cua o dich den A:\n");
    printf("Nhap dong: ");
    scanf("%d", &dong);
    printf("Nhap cot: ");
    scanf("%d", &cot);

    // Kiểm tra tọa độ A có hợp lệ không
    while (!checkCoordinate(dong, cot)) {
        printf("Gia tri cua dong phai tu 0 den 8!\n");
        printf("Gia tri cua cot phai tu 0 den 8!\n\n");
        printf("Hay nhap toa do cua o dich den A:\n");
        printf("Nhap dong: ");
        scanf("%d", &dong);
        printf("Nhap cot: ");
        scanf("%d", &cot);
    }
    // Tìm đường đi ngắn nhất
    findShortestPath(dong, cot);
    return 0;
}
