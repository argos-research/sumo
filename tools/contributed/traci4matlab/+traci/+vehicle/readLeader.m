function [vehID, dist] = readLeader(result)

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: readBestLanes.m 20 2015-03-02 16:52:32Z afacostag $

result.readInt();
result.read(1);
vehID = result.readString();
result.read(1);
dist = result.readDouble();




