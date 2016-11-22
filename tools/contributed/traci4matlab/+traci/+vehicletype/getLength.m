function length = getLength(typeID)
%getLength Returns the length in m of the vehicles of this type.
%   length = getLength(TYPEID) Returns the length in m of the vehicles of 
%   this type.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLength.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
length = traci.vehicletype.getUniversal(constants.VAR_LENGTH, typeID);