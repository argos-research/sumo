function emissionClass = getEmissionClass(typeID)
%getEmissionClass Returns the emission class of vehicles of this type.
%   emissionClass = getEmissionClass(TYPEID) Returns the emission class of 
%   vehicles of this type.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getEmissionClass.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
emissionClass = traci.vehicletype.getUniversal(constants.VAR_EMISSIONCLASS, typeID);