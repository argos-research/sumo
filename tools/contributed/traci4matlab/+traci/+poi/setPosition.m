function setPosition(poiID, x, y)
%setPosition Sets the position coordinates of the poi.
%   setPosition(POIID,X,Y) Sets the position coordinates of the poi.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setPosition.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
global message
traci.beginMessage(constants.CMD_SET_POI_VARIABLE, constants.VAR_POSITION, poiID, 1+8+8);
message.string = [message.string uint8(sscanf(constants.POSITION_2D,'%x')) traci.packInt64([y x])];
traci.sendExact();