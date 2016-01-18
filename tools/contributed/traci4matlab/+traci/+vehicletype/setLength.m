function setLength(typeID, length)
%setLength Sets the length in m of the vehicles of this type.
%   setLength(TYPEID,LENGTH) Sets the length in m of the vehicles of this 
%   type.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setLength.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_VEHICLETYPE_VARIABLE, constants.VAR_LENGTH, typeID, length);