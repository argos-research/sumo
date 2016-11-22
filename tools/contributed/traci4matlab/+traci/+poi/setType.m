function setType(poiID, poiType)
%setType Sets the (abstract) type of the poi.
%   setType(POIID,POITYPE) Sets the (abstract) type of the poi.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setType.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
global message
traci.beginMessage(constants.CMD_SET_POI_VARIABLE, constants.VAR_TYPE, poiID, 1+4+length(poiType));
message.string = [message.string uint8(sscanf(constants.TYPE_STRING,'%x'))...
	traci.packInt32(length(poiType)) uint8(poiType)];
traci.sendExact();