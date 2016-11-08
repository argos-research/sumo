function lanes = readBestLanes(result)
%readBestLanes Internal function to read information about the whish to use
%subsequent edge's lanes.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: readBestLanes.m 31 2016-09-28 15:16:56Z afacostag $

result.read(5);
nbLanes = result.readInt(); % Length
lanes = cell(1,nbLanes);
for i=1:nbLanes
    result.read(1);
    laneID = result.readString();
    result.read(1);
    len = result.readDouble();
    result.read(1);
    occupation = result.readDouble();
    result.read(1);
    offset = result.read(1);
    result.read(1);
    allowsContinuation = result.read(1);
    result.read(1);
    nextLanesNo = result.readInt();
    nextLanes = cell(1,nextLanesNo);
    for j=1:nextLanesNo
        nextLanes{j} = result.readString();
    end
    lanes{i} = {laneID, len, occupation, offset, allowsContinuation, nextLanes};
end