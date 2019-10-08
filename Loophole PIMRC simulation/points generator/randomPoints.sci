function getDist(iter)
    for i = 1:iter 
        genPoints(600, 50, 10, i)
    end
endfunction

function genPoints(R, UE, FAP, i)
    area = %pi * R/1000 * R/1000;
    
    r1 = R*sqrt(rand(UE * area,1));
    theta1 = 2*%pi*rand(UE * area,1);
    x1 = r1.*cos(theta1);
    y1 = r1.*sin(theta1);
    
    r2 = R*sqrt(rand(FAP * area,1));
    theta2 = 2*%pi*rand(FAP * area,1);
    x2 = r2.*cos(theta2);
    y2 = r2.*sin(theta2);
    
    fprintfMat("Data/UE-Dist-"+string(R)+"-"+string(UE)+"-"+string(i), [x1, y1], "%5.2f");
    fprintfMat("Data/FAP-Dist-"+string(R)+"-"+string(FAP)+"-"+string(i), [x2, y2], "%5.2f");
endfunction
