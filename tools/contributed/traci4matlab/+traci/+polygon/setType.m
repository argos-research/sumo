function setType(polygonID, polygonType)
%setType Sets the (abstract) type of the polygon.
%   setType(POLYGONID,POLYGONTTYPE) Sets the (abstract) type of the polygon.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setType.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
global message
traci.beginMessage(constants.CMD_SET_POLYGON_VARIABLE, constants.VAR_TYPE, polygonID, 1+4+length(polygonType));
message.string = [message.string uint8(sscanf(constants.TYPE_STRING,'%x')) ...
    traci.packInt32(length(polygonType)) uint8(polygonType)]; 
traci.sendExact();