    let main = document.getElementById('main');
    let month_dif = 0; // 오늘 날짜와 화면에 그려낼 달의 차이값
    
    
    function display(){
        let today = new Date(); // 오늘날짜
        let prtMonth = new Date(); // 화면에 그려낼 달
        // let startWeek = new Date(prtMonth); // 처음날짜 요일
        let startWeek = 0; // 처음날짜 요일
        let lastDay = 0 ; // 마지막 날짜
        let weekday = ['SUN','MON','TUE','WED','THU','FRI','SAT']; // 일 ~ 토 <배열> 
        let calendar = Array.from(Array(7), () => new Array(7).fill('0')); // 달력 출력용 빈 배열
        let result = ''; // 출력용 변수

        prtMonth.setDate(1);
        prtMonth.setMonth(today.getMonth() + month_dif);

        lastDay = prtMonth.getMonth() == 0 || prtMonth.getMonth() == 2 || prtMonth.getMonth() == 4 || prtMonth.getMonth() == 6 || 
                                    prtMonth.getMonth() == 7 || prtMonth.getMonth() == 9 || prtMonth.getMonth() == 11 ? 31 :
                    prtMonth.getMonth() != 1 ? 30 :
                    prtMonth.getFullYear() % 400 == 0 ? 29 :
                    prtMonth.getFullYear() % 100 == 0 ? 28 :
                    prtMonth.getFullYear() % 4 == 0 ? 29 : 28;

        // startWeek
        startWeek = prtMonth.getDay();
        result += "<table><tr>";

        let year = prtMonth.getFullYear();
        let month = prtMonth.getMonth();

        // let month = new Date(today);
        // 상단에 버튼 2개와 연도/달 표시 하는 단계
        result += "<div class='head'><button onclick = \"minus()\"><</button> " + 
                   year + "년 " + (month + 1) + "월" +
                   "<button onclick = \"plus()\">></button></div>";
            
        // 일 ~ 토 주 반복
        for(let j = 0; j<1; j++){
            result += "<tr>";
            for(let i = 0; i<7; i++){
                let span_weekend = i == 0 ? "class='sunday'" :
                                    i == 6 ? "class='saterday'" : "";

                
            }
            result += "</tr>";
        }

        let num = 1;

        for(let i = 0; i<calendar.length; i++){
            result += "<tr>";
            for(let j = 0; j<calendar[i].length; j++){
                
                let span_weekend = j == 0 ? "class='sunday'" :
                                   j == 6 ? "class='saterday'" : "";
                if(i==0){
                    result += "<td " + span_weekend + "><span>" + weekday[j] + "</span></td>";
                }else if(i==1 && j<startWeek){
                    result += "<td " + span_weekend + "></td>";
                }else if(num<=lastDay){
                    let span_beforDay = today.getFullYear() != prtMonth.getFullYear() ? span_weekend :
                                        today.getMonth() != prtMonth.getMonth() ? span_weekend :
                                        num < today.getDate() ? "class='beforDay'" : span_weekend;

                    let span_today = today.getFullYear() != prtMonth.getFullYear() ? "" :
                                     today.getMonth() != prtMonth.getMonth() ? "" : 
                                     today.getDate() == num ? "id=today" : "";
                    
                    let selday = "day"+ year + (month+1) + num; 
                    let dayId = "";
                    let daySelOnclick = "";
                    if (span_beforDay != "class='beforDay'"){
                        dayId = "id='"+selday+"'";
                        daySelOnclick = "onclick='selecting(this, "+year+", " + (month+1) + ", " + num +")'";
                    } 


                    calendar[i][j]= num;
                    result += "<td " + span_beforDay + " " + span_today +  " " + daySelOnclick + " " + dayId + ">" +calendar[i][j] +"</td>";
                    num ++;
                }else{
                    result += "<td></td>";
                }
                
            }
            result += "</tr>";
            if(num > lastDay) break;
        }

        result += "</table>";
        main.innerHTML = result;
		coloring();
    }
    
    function minus(){
        if(month_dif > 0) {
            month_dif --;
        }else alert('지난 달은 선택 할 수 없습니다');
        display();
    }
    function plus(){
        month_dif ++;
        display();
    }
    function show_calendar(){
        main.classList.toggle("noShow");
    }

	function selecting(selday, year, month, num){
	    let saveSel = document.querySelectorAll(".saveSel");
	    let saveSel1 = document.querySelector(".saveSel1");
	    let saveSel2 = document.querySelector(".saveSel2");
	    let saveSelWrap = document.querySelector(".saveSelWrap");
	    let show_month = month;
	    let show_num = num;
	    
	    if(month < 10) show_month = "0" + month;
	    if(num < 10) show_num = "0" + num;
	
	    let currentDateVal = "" + year + show_month + show_num;
	    let selElement = selday; 
	
	    let val0 = saveSel[0] ? saveSel[0].value : "";
	    let val1 = saveSel[1] ? saveSel[1].value : "";
	
	    if(currentDateVal == val0) {
	        selElement.classList.remove("selected");
	        saveSel1.innerHTML = "";
	    } 
	    else if(currentDateVal == val1) {
	        selElement.classList.remove("selected");
	        saveSel2.innerHTML = "";
	    } 
	    else {
	        if(saveSel.length >= 2){
	            alert("취소 후 재선택");
	        } else {
	            selElement.classList.add("selected");
	            
	            let inputHtml = "<input type='hidden' value='" + currentDateVal + "' name='selectedDays' class='saveSel'/>";
	            
	            if(saveSel1.innerHTML.trim() === "") {
	                saveSel1.innerHTML = inputHtml;
	            } else if(saveSel2.innerHTML.trim() === "") {
	                saveSel2.innerHTML = inputHtml;
	            }
	        }
	    }
	}
	
	function coloring() {
	    let saveSel = document.querySelectorAll(".saveSel");
	
	    saveSel.forEach(input => {
	        let val = input.value;
	        let targetId = "day" + val; 
	        
	        let targetTd = document.getElementById(targetId);
	        if (targetTd) {
	            targetTd.classList.add("selected");
	        }
	    });
	}

    display();