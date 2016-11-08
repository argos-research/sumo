function setLength(typeID, length)
%setLength Sets the length in m of the vehicles of this type.
%   setLength(TYPEID,LENGTH) Sets the length in m of the vehicles of this 
%   type.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setLength.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_VEHICLETYPE_VARIABLE, constants.VAR_LENGTH, typeID, length);